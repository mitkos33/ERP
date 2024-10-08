package com.wg.erp.crm.model.entity;

import com.wg.erp.crm.model.enums.OrderStatus;
import com.wg.erp.model.entity.BaseEntity;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.entity.UserGroup;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private String orderNumber;

    private String name;

    @ManyToOne
    private OrderType orderType;

    @ManyToOne
    private Document document;

    private String documentFileName;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserGroup> groups;

    @ManyToOne
    private User createdBy;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getOrderNumber() {
        return orderNumber;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
