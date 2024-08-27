package com.wg.erp.web;


import com.wg.erp.model.user.ErpUserDetailsModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.HashSet;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testCustomersList() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddCustomer() throws Exception {

        setupSecurityContext();
        mockMvc.perform(post("/customers/add")
                .param("firstName", "Test")
                .param("lastName", "User")
                .param("email", "test@test.com")
                .param("phone", "12345678")
                .param("eik", "1234567890")
                .param("city", "bg_Bg")
                .param("mol", "Test User")
                .param("client_id","")

                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));

        SecurityContextHolder.clearContext();
    }

    @Test
    void testEditCustomer() throws Exception {
        setupSecurityContext();
        mockMvc.perform(post("/customers/add")
                        .param("firstName", "Test edit")
                        .param("lastName", "User edit")
                        .param("email", "test@test.com")
                        .param("phone", "87654321")
                        .param("eik", "1234567890")
                        .param("city", "bg_Bg")
                        .param("client_id","25")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        setupSecurityContext();
        mockMvc.perform(delete("/api/customers/24")
                .with(csrf())
        ).andExpect(status().is(204));
    }

    private void setupSecurityContext() {
        ErpUserDetailsModel userDetails = new ErpUserDetailsModel(
                "testUsername", "testPassword",
                AuthorityUtils.createAuthorityList("ROLE_ADMIN"),
                "Test", "User", "test@test.com", new HashSet<>()
        );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
