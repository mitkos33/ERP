package com.wg.erp.crm.service;

import com.wg.erp.crm.model.entity.OrderType;
import com.wg.erp.crm.repository.OrderTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderTypeService {

    private final OrderTypeRepository orderTypeRepository;

    public OrderTypeService(OrderTypeRepository orderTypeRepository) {
        this.orderTypeRepository = orderTypeRepository;
    }

    public List<OrderType> getAllOrderTypes() {
        return orderTypeRepository.findAll();
    }


}
