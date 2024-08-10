package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.model.dto.ClientDetailDTO;
import com.wg.erp.crm.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {


    private final ClientService clientService;

    CustomerRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClientDetailDTO>> getAllCustomers() {
        return ResponseEntity.ok(clientService.getAllClientsDetail());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailDTO> customerById( @PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientAddDTO> deleteCustomerById(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PostMapping("/add")
    public ResponseEntity<ClientAddDTO> addCustomer(@RequestBody ClientAddDTO client) {
        clientService.addClient(client, "test@test.com");
        return ResponseEntity
            .ok()
            .body(client);
    }
}
