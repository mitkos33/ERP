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


@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testTasksList() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddTask() throws Exception {

        setupSecurityContext();
        mockMvc.perform(post("/tasks/add")
                .param("title", "Test task")
                .param("description", "Test description")
                .param("dueDate", "2024-08-10T23:59:59")
                .param("status", "OPEN")
                .param("priority", "HIGH")
                .param("order", "1")
                .param("task_id","")
                .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        SecurityContextHolder.clearContext();
    }

    @Test
    void testEditTask() throws Exception {
        setupSecurityContext();
        mockMvc.perform(post("/tasks/add")
                .param("title", "Test task edit")
                .param("description", "Test edit description")
                .param("dueDate", "2024-08-11T23:59:59")
                .param("status", "OPEN")
                .param("priority", "MEDIUM")
                .param("order", "1")
                .param("task_id","11")
                .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));
    }

    @Test
    void testDeleteTask() throws Exception {
        setupSecurityContext();
        mockMvc.perform(delete("/api/tasks/15")
                .with(csrf())
        ).andExpect(status().is(204));
    }

    private void setupSecurityContext() {
        ErpUserDetailsModel userDetails = new ErpUserDetailsModel(
                "testUsername", "testPassword",
                AuthorityUtils.createAuthorityList("ROLE_ADMIN"),
                "Test", "User", "test@test.com", new ArrayList<>()
        );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
