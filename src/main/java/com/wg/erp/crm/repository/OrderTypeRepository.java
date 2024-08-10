package com.wg.erp.crm.repository;

import com.wg.erp.crm.model.entity.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {
    List<OrderType> findAllByName(String name);
}
