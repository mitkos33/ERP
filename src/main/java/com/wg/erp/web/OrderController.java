package com.wg.erp.web;


import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/find")
    public String findOrders() {
        return "find-orders";
    }

    @GetMapping("/find-word")
    public String findOrdersByWord(Model model, String searchOrder) {
        try {
            Optional<Order> order = orderService.findOrdersByOrderNumber(searchOrder);
            model.addAttribute("order", order);
            return "find-orders";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Order not found");
            return "find-orders";
        }


    }
}
