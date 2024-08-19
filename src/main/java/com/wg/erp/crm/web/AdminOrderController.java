package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.OrderAddDTO;
import com.wg.erp.crm.model.enums.OrderStatus;
import com.wg.erp.crm.service.DocumentService;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.OrderTypeService;
import com.wg.erp.model.user.ErpUserDetailsModel;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;
    private final OrderTypeService orderTypeService;
    private final DocumentService documentService;

    public AdminOrderController(OrderService orderService, OrderTypeService orderTypeService, DocumentService documentService) {
        this.orderService = orderService;
        this.orderTypeService = orderTypeService;
        this.documentService = documentService;
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

    @GetMapping("/add")
    public String addOrder(Model model) {
        model.addAttribute("orderTypes", orderTypeService.getAllOrderTypes());
        model.addAttribute("documents", documentService.getAllDocuments());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderAddDTO", new OrderAddDTO());
        return "admin/order-add";
    }

    @GetMapping("/add/{orderId}")
    public String addOrder(Model model, @PathVariable String orderId) {
        model.addAttribute("orderTypes", orderTypeService.getAllOrderTypes());
        model.addAttribute("documents", documentService.getAllDocuments());
        model.addAttribute("orderAddDTO", orderService.findOrderById(orderId));
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderId", orderId);
        return "admin/order-add";
    }

    @PostMapping("/add")
    public String addOrder(@Valid @ModelAttribute("orderAddDTO") OrderAddDTO orderAddDTO, @RequestParam("orderId") String orderId,
                           RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderAddDTO", orderAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderAddDTO", bindingResult);
            return "redirect:/admin/orders/add";
        }
        if (!orderId.isBlank() && !orderId.isEmpty()) {
            orderService.updateOrder(orderAddDTO, orderId);
        }
        else {
            orderService.addOrder(orderAddDTO);
        }
        return "redirect:/admin/orders";
    }



}
