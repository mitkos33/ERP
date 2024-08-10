package com.wg.erp.crm.service;

import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.model.dto.TaskAddDTO;
import com.wg.erp.crm.model.entity.Client;
import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.model.enums.StatusType;
import com.wg.erp.crm.repository.TaskRepository;
import com.wg.erp.model.entity.User;
import com.wg.erp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

    public void addTask(TaskAddDTO taskAddDTO, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with email " + email + " not found!");
        }
        Task taskEntity = mapTaskAddDTO(taskAddDTO);
        taskEntity.setCreatedBy(user.get());
        this.taskRepository.save(taskEntity);
    }

    public Task mapTaskAddDTO(TaskAddDTO taskAddDTO) {
        return  modelMapper.map(taskAddDTO, Task.class);
    }

    public void updateTask(TaskAddDTO taskAddDTO, long taskId) {
        Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " not found!"));
        modelMapper.map(taskAddDTO, task);
        this.taskRepository.save(task);
    }

    public TaskAddDTO findById(Long id) {
        Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found!"));
        return modelMapper.map(task, TaskAddDTO.class);
    }

    public void deleteTask(Long id) {
        Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found!"));
        this.taskRepository.delete(task);
    }
}
