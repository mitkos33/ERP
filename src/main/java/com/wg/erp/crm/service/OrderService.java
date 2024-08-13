package com.wg.erp.crm.service;

import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public int getOrdersCount() {
        return orderRepository.findAll().size();
    }


    public Optional<Order> findOrdersByName(String searchOrder) {
        return  orderRepository.findOrdersByName(searchOrder);
    }

    public Optional<Order> findOrdersByOrderNumber(String orderId) {
        Optional<Order> order = orderRepository.findOrdersByOrderNumber(orderId);
        if (order.isPresent()) {
            return order;
        }
        throw new IllegalArgumentException("Order not found");
    }
}
