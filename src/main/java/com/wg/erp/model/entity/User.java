package com.wg.erp.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserGroup> users_groups;

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<UserGroup> getUsers_groups() {
        return users_groups;
    }

    public User setUsers_groups(List<UserGroup> users_groups) {
        this.users_groups = users_groups;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
