package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {


    private final ClientService clientService;

    CustomerRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientAddDTO> deleteCustomerById(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
