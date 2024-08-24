package com.wg.erp.crm.model.dto;

import com.wg.erp.crm.model.entity.Document;
import com.wg.erp.crm.model.entity.OrderType;
import com.wg.erp.crm.model.enums.OrderStatus;
import com.wg.erp.model.entity.UserGroup;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class OrderAddDTO {

    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
    private OrderType orderType;
    private Document document;

    private List<UserGroup> groups;

    @Enumerated
    private OrderStatus status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderTypeId) {
        this.orderType = orderTypeId;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }


}
