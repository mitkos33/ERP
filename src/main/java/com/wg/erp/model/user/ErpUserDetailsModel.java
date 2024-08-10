package com.wg.erp.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ErpUserDetailsModel extends User {

    private final String firstName;
    private final String lastName;
    private final String email;

    public ErpUserDetailsModel(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String firstName,
            String lastName,
            String email
    ) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
}