package com.wg.erp.crm.web;

import com.wg.erp.crm.repository.ClientRepository;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.TaskService;
import com.wg.erp.model.user.ErpUserDetailsModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class DashboardController {


    private final ClientRepository clientRepository;
    private final TaskService taskService;
    private final OrderService orderService;


    public DashboardController(ClientRepository clientRepository, TaskService taskService, OrderService orderService) {
        this.clientRepository = clientRepository;
        this.taskService = taskService;
        this.orderService = orderService;
    }

    @ModelAttribute("userName")
    public String getUserName(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof ErpUserDetailsModel erpUserDetails) {
            return erpUserDetails.getFullName();
        } else {
            return "Anonymous";
        }
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, @AuthenticationPrincipal ErpUserDetailsModel userDetails) {

        model.addAttribute("clientsCount", this.clientRepository.count());
        model.addAttribute("tasksCount", this.taskService.countAllOpenTasks(userDetails));
        model.addAttribute("ordersCount", this.orderService.getOrdersCount());
        return "admin/dashboard";
    }
}
