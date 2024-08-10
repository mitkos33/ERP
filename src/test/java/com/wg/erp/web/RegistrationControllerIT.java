package com.wg.erp.web;

import com.wg.erp.model.entity.User;
import com.wg.erp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegistration() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("email", "ivan.ivanov@example.com")
                        .param("firstName", "Иван")
                        .param("lastName", "Иванов")
                        .param("password", "top1234")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

        Optional<User> userEntityOpt = userRepository.findByEmail("ivan.ivanov@example.com");

        Assertions.assertTrue(userEntityOpt.isPresent());

        User userEntity = userEntityOpt.get();

        Assertions.assertEquals("Иван", userEntity.getFirstName());
        Assertions.assertEquals("Иванов", userEntity.getLastName());

        Assertions.assertTrue(passwordEncoder.matches("top1234", userEntity.getPassword()));
    }
}
