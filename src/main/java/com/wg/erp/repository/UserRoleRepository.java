package com.wg.erp.repository;

import com.wg.erp.model.entity.UserRole;
import com.wg.erp.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(UserRoleEnum userRoleEnum);
}
