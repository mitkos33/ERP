package com.wg.erp.crm.web;

import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.OrderTypeService;
import com.wg.erp.model.user.ErpUserDetailsModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderTypeService orderTypeService;

    public OrderController(OrderService orderService, OrderTypeService orderTypeService) {
        this.orderService = orderService;
        this.orderTypeService = orderTypeService;
    }

    @ModelAttribute("userName")
    public String getUserName(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof ErpUserDetailsModel erpUserDetails) {
            return erpUserDetails.getFullName();
        } else {
            return "Anonymous";
        }
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
    @GetMapping("/find-word")
    public String findOrdersByWord(Model model, String searchOrder, RedirectAttributes redirectAttributes) {
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
