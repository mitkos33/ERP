package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.TaskAddDTO;
import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.model.enums.PriorityType;
import com.wg.erp.crm.model.enums.StatusType;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.TaskService;
import com.wg.erp.model.user.ErpUserDetailsModel;
import com.wg.erp.service.UserGroupService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin/tasks")
public class AdminTaskController {


    private final TaskService taskService;
    private final OrderService orderService;
    private final UserGroupService userGroupService;


    AdminTaskController(TaskService taskService, OrderService orderService, UserGroupService
                        userGroupService) {

        this.taskService = taskService;
        this.orderService = orderService;
        this.userGroupService = userGroupService;
    }

    @ModelAttribute("userName")
    public String getUserName(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof ErpUserDetailsModel erpUserDetails) {
            return erpUserDetails.getFullName();
        } else {
            return "Anonymous";
        }
    }

    @GetMapping("")
    public String getAllTasks(Model model, @AuthenticationPrincipal ErpUserDetailsModel userDetails) {

        Map<String, List<Task>> allOpenTasks = taskService.getAllActiveTasks(userDetails);
        List<Task> allDoneTasks = taskService.getAllDoneTasks(userDetails);

        Map<String, List<Task>> otherTasks = new LinkedHashMap<>();

        if (allOpenTasks.containsKey("Other")) {
            otherTasks = taskService.filterOtherTasksByDate(allOpenTasks.get("Other"));
        }


        List<Task> todayTasks = new ArrayList<>();
        if (allOpenTasks.containsKey("Today")) {
            todayTasks = allOpenTasks.get("Today");
        }

        model.addAttribute("todayTasks", todayTasks);
        model.addAttribute("todayTasksCount", todayTasks.size());
        model.addAttribute("otherTasks", otherTasks);
        model.addAttribute("allDoneTasks", allDoneTasks);
        model.addAttribute("allOpenTaskCount", taskService.countAllOpenTasks(userDetails));
        model.addAttribute("priorityTypes", PriorityType.values());

        return "admin/tasks";
    }

    @GetMapping("/add")
    public String addTask(Model model) {

        if (!model.containsAttribute("taskAddDTO")) {
            model.addAttribute("taskAddDTO", new TaskAddDTO());
        }
        model.addAttribute("usersGroups", userGroupService.getAllUserGroups());
        model.addAttribute("priorityTypes", PriorityType.values());
        model.addAttribute("statusTypes", StatusType.values());
        model.addAttribute("orders", orderService.getAllOrders());

        return "admin/task-add";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute("taskAddDTO") TaskAddDTO taskAddDTO, @RequestParam("task_id") String taskId,
                          BindingResult result,
                          RedirectAttributes rAtt, @AuthenticationPrincipal ErpUserDetailsModel userDetails) {

        if(result.hasErrors()){
            rAtt.addFlashAttribute("taskAddDTO", taskAddDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.taskAddDTO", result);
            return "redirect:/admin/tasks/add";
        }

        if (!taskId.isEmpty() && !taskId.isBlank()) {
            this.taskService.updateTask(taskAddDTO, Long.parseLong(taskId));
        }
        else {
            this.taskService.addTask(taskAddDTO, userDetails.getEmail());
        }

        return "redirect:/admin/tasks";

    }

    @GetMapping("/add/{id}")
    public String editTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal ErpUserDetailsModel userDetails) {

        TaskAddDTO task = null;
        try {
            task = this.taskService.findByIdAndUser(id,userDetails);
        }
        catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/tasks";
        }

        model.addAttribute("taskAddDTO", task);
        model.addAttribute("priorityTypes", PriorityType.values());
        model.addAttribute("statusTypes", StatusType.values());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("usersGroups", userGroupService.getAllUserGroups());
        model.addAttribute("task_id", id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDate = task.getDueDate().format(formatter);
        model.addAttribute("formattedDueDate", formattedDate);
        return "admin/task-add";
    }

}
