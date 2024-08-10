package com.wg.erp.crm.repository;

import com.wg.erp.crm.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
