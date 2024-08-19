package com.wg.erp.crm.service;

import com.wg.erp.crm.model.dto.OrderAddDTO;
import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
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

    public void addOrder(OrderAddDTO orderAddDTO) {
        orderRepository.save(mapOrderAddDTOToOrder(orderAddDTO));
    }

    public Order mapOrderAddDTOToOrder(OrderAddDTO orderAddDTO) {
        return  modelMapper.map(orderAddDTO, Order.class);
    }

    public OrderAddDTO findOrderById(String orderId) {

        Optional<Order> order = orderRepository.findById(Long.parseLong(orderId));
        if (order.isPresent()) {
            return modelMapper.map(order.get(), OrderAddDTO.class);
        }
        throw new IllegalArgumentException("Order not found");
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void updateOrder(OrderAddDTO orderAddDTO, String orderId) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found!"));
        modelMapper.map(orderAddDTO, order);
        orderRepository.save(order);
    }
}
