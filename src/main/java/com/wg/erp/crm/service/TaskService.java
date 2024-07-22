package com.wg.erp.crm.service;

import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.model.enums.StatusType;
import com.wg.erp.crm.repository.TaskRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Map<String,List<Task>> getAllActiveTasks() {

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime tomorrow = today.plusDays(1);
        Set<StatusType> status = Set.of(StatusType.OPEN, StatusType.IN_PROGRESS);
        List<Task> allTasks = taskRepository.findAllByStatusInOrderByDueDateDesc(status);

        Map<String, List<Task>> allOpenTasks = new HashMap<>();
        allTasks.stream()
                .filter(task -> task.getDueDate() != null)
                .forEach(task -> {
                    String key =  task.getDueDate().isAfter(today) && task.getDueDate().isBefore(tomorrow) ? "Today" : "Other";
                    allOpenTasks.computeIfAbsent(key, k -> new ArrayList<>()).add(task);
                });

        return allOpenTasks;
    }

    public List<Task> getAllDoneTasks() {
        Set<StatusType> status = Set.of(StatusType.DONE);
        return taskRepository.findAllByStatusInOrderByDueDateDesc(status);
    }

    public Map<String, List<Task>> filterOtherTasksByDate(List<Task> tasks) {

        Map<String, List<Task>> otherTasks = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        tasks.stream()
                .filter(task -> task.getDueDate() != null)
                .forEach(task -> {
                    String key =  task.getDueDate().format(formatter);
                    otherTasks.computeIfAbsent(key, k -> new ArrayList<>()).add(task);
                });

        return otherTasks;
    }
}
