package com.wg.erp.crm.model.entity;

import com.wg.erp.model.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private String name;

    @ManyToOne
    private OrderType orderType;

    @ManyToOne
    private Document document;

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


}
