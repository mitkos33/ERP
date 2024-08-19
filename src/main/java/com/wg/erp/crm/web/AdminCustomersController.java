package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.service.ClientService;
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
@RequestMapping("/admin/customers")
public class AdminCustomersController {

    private final ClientService clientService;

    public AdminCustomersController(ClientService clientService) {
        this.clientService = clientService;
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
    public String getAllCustomers(Model model) {
        model.addAttribute("allCustomers", this.clientService.getAllClients());
        return "admin/customers";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        if (!model.containsAttribute("addClientDTO")) {
            model.addAttribute("addClientDTO", ClientAddDTO.empty());
        }
        return "admin/customer-add";
    }

    @PostMapping("/add")
    public String addCustomerSubmit(@Valid @ModelAttribute("clientAddDTO") ClientAddDTO clientAddDTO, @RequestParam("client_id") String id,
                                    BindingResult result,
                                    RedirectAttributes rAtt, @AuthenticationPrincipal ErpUserDetailsModel userDetails) {

        if(result.hasErrors()){
            rAtt.addFlashAttribute("addClientDTO", clientAddDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addClientDTO", result);
            return "redirect:/admin/customers/add";
        }

        if (!id.isEmpty() && !id.isBlank()) {
            this.clientService.updateClient(clientAddDTO, Long.parseLong(id));
        }
        else {
            this.clientService.addClient(clientAddDTO, userDetails.getEmail());
        }

        return "redirect:/admin/customers";
    }

    @GetMapping("/add/{id}")
    public String addCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("addClientDTO", this.clientService.getClientById(id));
        model.addAttribute("client_id", id);
        return "admin/customer-add";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return "redirect:/admin/customers";
    }
}
