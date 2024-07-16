package com.wg.erp.service;

import com.wg.erp.model.entity.User;
import com.wg.erp.model.entity.UserRole;
import com.wg.erp.model.enums.UserRoleEnum;
import com.wg.erp.model.user.ErpUserDetailsModel;
import com.wg.erp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class ErpUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public ErpUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(ErpUserDetailService::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    private static UserDetails map(User userEntity) {

        return new ErpUserDetailsModel(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(UserRole::getRole).map(ErpUserDetailService::map).toList(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail()
        );
    }

    private static GrantedAuthority map(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
