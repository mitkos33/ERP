package com.wg.erp.service;


import com.wg.erp.model.dto.UserRegistrationDTO;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.entity.UserRole;
import com.wg.erp.model.enums.UserRoleEnum;
import com.wg.erp.repository.UserRepository;
import com.wg.erp.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;

    public UserService(ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository, UserRoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public void registerUser(UserRegistrationDTO userRegistration) {
        userRepository.save(map(userRegistration));
    }

    private User map(UserRegistrationDTO userRegistrationDTO) {
        User mappedEntity = modelMapper.map(userRegistrationDTO, User.class);
        mappedEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        Optional<UserRole> role = roleRepository.findByRole(UserRoleEnum.USER);
        if (role.isPresent()) {
            List<UserRole> roles = new ArrayList<>();
            roles.add(role.get());
            mappedEntity.setRoles(roles);
        }

        return mappedEntity;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}