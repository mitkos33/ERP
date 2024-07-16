package com.wg.erp.crm.web;

import com.wg.erp.crm.repository.ClientRepository;
import com.wg.erp.model.user.ErpUserDetailsModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DashboardController {


    private final ClientRepository clientRepository;

    public DashboardController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
    public String getDashboard(Model model) {

        model.addAttribute("clientsCount", this.clientRepository.count());
        return "admin/dashboard";
    }
}
