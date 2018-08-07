package com.thoughtworks.training.gateway1.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizeEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        int scUnauthorized = HttpServletResponse.SC_UNAUTHORIZED;

        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Access Deny");
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
