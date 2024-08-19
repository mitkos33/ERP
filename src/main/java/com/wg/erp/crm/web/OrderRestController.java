package com.wg.erp.crm.web;

import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.model.dto.OrderAddDTO;
import com.wg.erp.crm.service.OrderService;
import com.wg.erp.crm.service.TaskService;
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
    public ResponseEntity<OrderAddDTO> deleteTaskById(@PathVariable("id") Long id) {
        this.orderService.deleteOrder(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
