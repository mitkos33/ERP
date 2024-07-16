package com.wg.erp.init;

import com.wg.erp.model.entity.UserRole;
import com.wg.erp.model.enums.UserRoleEnum;
import com.wg.erp.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserRolesInit  implements CommandLineRunner  {

    private final UserRoleRepository userRoleRepository;


    public UserRolesInit(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() != UserRoleEnum.values().length) {
            userRoleRepository.deleteAll();
            for (UserRoleEnum value : UserRoleEnum.values()) {
                UserRole userRole = new UserRole();
                userRole.setRole(value);
                userRoleRepository.save(userRole);
            }
        }
    }
}