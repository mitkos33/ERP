package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.OrderAddDTO;
import com.wg.erp.crm.model.enums.OrderStatus;
import com.wg.erp.crm.service.DocumentService;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.OrderTypeService;
import com.wg.erp.model.user.ErpUserDetailsModel;
import com.wg.erp.service.UserGroupService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;
    private final OrderTypeService orderTypeService;
    private final DocumentService documentService;
    private final UserGroupService userGroupService;

    public AdminOrderController(OrderService orderService, OrderTypeService orderTypeService, DocumentService documentService, UserGroupService userGroupService) {
        this.orderService = orderService;
        this.orderTypeService = orderTypeService;
        this.documentService = documentService;
        this.userGroupService = userGroupService;
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
        model.addAttribute("usersGroups", userGroupService.getAllUserGroups());
        model.addAttribute("orderAddDTO", new OrderAddDTO());
        return "admin/order-add";
    }

    @GetMapping("/add/{orderId}")
    public String addOrder(Model model, @PathVariable String orderId) {
        model.addAttribute("orderTypes", orderTypeService.getAllOrderTypes());
        model.addAttribute("documents", documentService.getAllDocuments());
        model.addAttribute("usersGroups", userGroupService.getAllUserGroups());
        model.addAttribute("orderAddDTO", orderService.findOrderById(orderId));
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderId", orderId);
        return "admin/order-add";
    }

    @PostMapping("/add")
    public String addOrder(@Valid @ModelAttribute("orderAddDTO") OrderAddDTO orderAddDTO, @RequestParam("file") MultipartFile file, @RequestParam("orderId") String orderId,
                           RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderAddDTO", orderAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderAddDTO", bindingResult);
            return "redirect:/admin/orders/add";
        }

        String documentFileName = "";
        if (file != null && !file.isEmpty()) {

            documentFileName = file.getOriginalFilename();
            try {
                documentService.saveDocument(file);
            } catch (Exception e) {
               System.out.println("Error saving file: " + e.getMessage());
            }
        }
        if (!orderId.isBlank() && !orderId.isEmpty()) {
            orderService.updateOrder(orderAddDTO, documentFileName, orderId);
        }
        else {
            orderService.addOrder(orderAddDTO, documentFileName);
        }
        return "redirect:/admin/orders";
    }



}
