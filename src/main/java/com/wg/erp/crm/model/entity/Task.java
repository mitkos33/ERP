package com.wg.erp.crm.model.entity;


import com.wg.erp.model.entity.BaseEntity;
import com.wg.erp.crm.model.enums.PriorityType;
import com.wg.erp.crm.model.enums.StatusType;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.entity.UserGroup;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityType priority;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dueDate;

    @ManyToOne
    private Order order;

    @ManyToOne
    private User createdBy;


    @ManyToMany
    private List<UserGroup> assignedToGroups;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<UserGroup> getAssignedToGroups() {
        return assignedToGroups;
    }

    public void setAssignedToGroups(List<UserGroup> assignedToGroups) {
        this.assignedToGroups = assignedToGroups;
    }
}
