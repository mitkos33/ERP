package com.wg.erp.crm.web;

import com.wg.erp.crm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable("id") Long id) {
        try {
            this.orderService.deleteOrder(id);
        }
        catch (Exception e) {
          return ResponseEntity
                  .status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity
                .noContent()
                .build();
    }
}
