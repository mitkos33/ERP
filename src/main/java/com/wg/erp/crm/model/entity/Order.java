package com.wg.erp.crm.model.entity;

import com.wg.erp.crm.model.enums.OrderStatus;
import com.wg.erp.model.entity.BaseEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private String orderNumber;

    private String name;

    @ManyToOne
    private OrderType orderType;

    @ManyToOne
    private Document document;

    @Enumerated(EnumType.STRING)
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


}
