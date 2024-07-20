package com.wg.erp.config;

import com.wg.erp.model.enums.UserRoleEnum;
import com.wg.erp.repository.UserRepository;
import com.wg.erp.service.ErpUserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/plugins/**").permitAll()
                                        .requestMatchers("/", "/users/login","users/login-error", "/users/register").permitAll()
                                        .requestMatchers("/tasks/**","/customers","/customers/**")
                                            .hasAnyRole(
                                                UserRoleEnum.ADMIN.name(),
                                                UserRoleEnum.SALES.name())
                                        .anyRequest()
                                        .authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/dashboard", true)
                                .failureUrl("/users/login-error")
                )
                .rememberMe(
                        rememberMe ->
                                rememberMe
                                        .key("a-secret-key")
                                        .tokenValiditySeconds(1209600)
                )
                .logout(
                        logout ->
                                logout
                                        .logoutUrl("/users/logout")
                                        .logoutSuccessUrl("/")
                                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public ErpUserDetailService userDetailsService(UserRepository userRepository) {
        return new ErpUserDetailService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }
}