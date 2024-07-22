package com.wg.erp.crm.web;

import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {


    private final TaskService taskService;

    TaskController(TaskService taskService) {

        this.taskService = taskService;
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
        model.addAttribute("allOpenTaskCount", allDoneTasks.size() + todayTasks.size());

        return "admin/tasks";
    }

    @GetMapping("/add")
    public String addTask() {
        return "task-add";
    }

}
