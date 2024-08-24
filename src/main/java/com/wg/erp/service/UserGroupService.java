package com.wg.erp.service;

import com.wg.erp.model.entity.UserGroup;
import com.wg.erp.repository.UserGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    public UserGroupService(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    public List<UserGroup> getAllUserGroups() {
        return this.userGroupRepository.findAll();
    }
}
