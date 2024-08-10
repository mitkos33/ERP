package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TaskService taskService;

    TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientAddDTO> deleteTaskById(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
