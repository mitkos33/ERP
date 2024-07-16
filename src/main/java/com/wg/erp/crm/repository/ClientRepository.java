package com.wg.erp.crm.repository;

import com.wg.erp.crm.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
