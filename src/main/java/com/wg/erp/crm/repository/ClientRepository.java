package com.wg.erp.crm.repository;

import com.wg.erp.crm.model.entity.Client;
import com.wg.erp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByCreatedByOrOwner(User user, User owner);
}
