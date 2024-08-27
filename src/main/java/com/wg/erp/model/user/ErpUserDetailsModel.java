package com.wg.erp.model.user;

import com.wg.erp.model.entity.UserGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ErpUserDetailsModel extends User {

    private final String firstName;
    private final String lastName;
    private final String email;
    private Set<UserGroup> userGroups;

    public ErpUserDetailsModel(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String firstName,
            String lastName,
            String email,
            Set<UserGroup> userGroups
    ) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userGroups = userGroups;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) {
            fullName.append(firstName);
        }
        if (lastName != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(lastName);
        }

        return fullName.toString();
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }
}