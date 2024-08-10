package com.wg.erp.crm.model.dto;

import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.model.enums.PriorityType;
import com.wg.erp.crm.model.enums.StatusType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TaskAddDTO {

    @NotEmpty
    private String title;

    private String description;

    private StatusType status;

    private PriorityType priority;

    private LocalDateTime dueDate;

    private Order order;

    public @NotEmpty String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
