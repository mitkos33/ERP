package com.wg.erp.crm.repository;

import com.wg.erp.crm.model.entity.Order;
import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrdersByName(String searchOrder);
    Optional<Order> findOrdersByOrderNumber(String orderNumber);

    @Query("SELECT o FROM Order o WHERE o.createdBy = :createdBy OR EXISTS (SELECT g FROM UserGroup g WHERE g MEMBER OF o.groups AND g IN :assignedToGroupIds)")
    List<Order> findAllOrderByAuth(
            @Param("createdBy") User createdBy,
            @Param("assignedToGroupIds") Set<UserGroup> assignedToGroupIds
    );

    List<Order> findAllByCreatedBy(User user);
}
