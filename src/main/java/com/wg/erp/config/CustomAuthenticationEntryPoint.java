package com.wg.erp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getRequestURI().startsWith("/api")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No found!");
            return;
        }
        if (request.getRequestURI().startsWith("/admin")) {
            response.sendRedirect("/users/login");
            return;
        }
        response.sendRedirect("/error/404");  // Redirect to custom 404 page
    }
}