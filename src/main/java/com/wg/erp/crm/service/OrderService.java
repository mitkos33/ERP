package com.wg.erp.crm.service;

import com.wg.erp.crm.model.dto.OrderAddDTO;
import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.repository.OrderRepository;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.entity.UserGroup;
import com.wg.erp.model.user.ErpUserDetailsModel;
import com.wg.erp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public OrderService(OrderRepository orderRepository,   ModelMapper modelMapper, UserRepository userRepository ) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
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

    public void addOrder(OrderAddDTO orderAddDTO, String documentFileName, ErpUserDetailsModel userDetails) {

        Optional<User> user = userRepository.findByEmail(userDetails.getEmail());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with email " + userDetails.getEmail() + " not found!");
        }
        Order order = modelMapper.map(orderAddDTO, Order.class);
        order.setDocumentFileName(documentFileName);
        order.setCreatedBy(user.get());
        orderRepository.save(order);
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

    public void deleteOrder(Long id) throws Exception {
        try {
            orderRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateOrder(OrderAddDTO orderAddDTO, String documentFileName, String orderId) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found!"));

        order.setGroups(new ArrayList<>());
        orderRepository.save(order);

        modelMapper.map(orderAddDTO, order);
        order.setDocumentFileName(documentFileName);
        orderRepository.save(order);
    }

    public List<Order> getAllOrdersByAuth(ErpUserDetailsModel userDetails) {
        Optional<User> user = userRepository.findByEmail(userDetails.getEmail());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with email " + userDetails.getEmail() + " not found!");
        }
        Set<UserGroup> userGroups = userDetails.getUserGroups();
        return orderRepository.findAllOrderByAuth(user.get(), userGroups);
    }
}
