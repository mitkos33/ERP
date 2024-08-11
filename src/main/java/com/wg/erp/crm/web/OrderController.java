package com.wg.erp.crm.web;

import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.OrderTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderTypeService orderTypeService;

    public OrderController(OrderService orderService, OrderTypeService orderTypeService) {
        this.orderService = orderService;
        this.orderTypeService = orderTypeService;
    }

    @GetMapping("")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("orderTypes", orderTypeService.getAllOrderTypes());
        return "admin/orders";
    }

    @GetMapping("/find")
    public String findOrders(Model model) {
        return "find-orders";

    }
    @GetMapping("/find/{searchOrder}")
    public String findOrdersByWord(Model model, String searchOrder) {
        Optional<Order> order = orderService.findOrdersByName(searchOrder);
        model.addAttribute("order", order);
        return "find-orders";
    }

}
