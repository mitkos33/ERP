package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.TaskAddDTO;
import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.model.enums.PriorityType;
import com.wg.erp.crm.model.enums.StatusType;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.TaskService;
import com.wg.erp.model.user.ErpUserDetailsModel;
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
@RequestMapping("/tasks")
public class TaskController {


    private final TaskService taskService;
    private final OrderService orderService;


    TaskController(TaskService taskService, OrderService orderService) {

        this.taskService = taskService;
        this.orderService = orderService;
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
    public String getAllTasks(Model model) {

        Map<String, List<Task>> allOpenTasks = taskService.getAllActiveTasks();
        List<Task> allDoneTasks = taskService.getAllDoneTasks();

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
        model.addAttribute("allOpenTaskCount", otherTasks.size() + todayTasks.size());
        model.addAttribute("priorityTypes", PriorityType.values());

        return "admin/tasks";
    }

    @GetMapping("/add")
    public String addTask(Model model) {

        if (!model.containsAttribute("taskAddDTO")) {
            model.addAttribute("taskAddDTO", new TaskAddDTO());
        }

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
            return "redirect:/tasks/add";
        }

        if (!taskId.isEmpty() && !taskId.isBlank()) {
            this.taskService.updateTask(taskAddDTO, Long.parseLong(taskId));
        }
        else {
            this.taskService.addTask(taskAddDTO, userDetails.getEmail());
        }

        return "redirect:/tasks";

    }

    @GetMapping("/add/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        TaskAddDTO task = this.taskService.findById(id);
        model.addAttribute("taskAddDTO", task);
        model.addAttribute("priorityTypes", PriorityType.values());
        model.addAttribute("statusTypes", StatusType.values());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("task_id", id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDate = task.getDueDate().format(formatter);
        model.addAttribute("formattedDueDate", formattedDate);
        return "admin/task-add";
    }

}
