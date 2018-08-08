package com.thoughtworks.training.gateway1.security;

import com.netflix.zuul.context.RequestContext;
import com.thoughtworks.training.gateway1.client.UserClient;
import com.thoughtworks.training.gateway1.dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

@Component
public class ToDoAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try {

            String token = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (!StringUtils.isEmpty(token)) {

                String internalToken = jwtTokenGenerateInternalToken(token);

                RequestContext requestContext = RequestContext.getCurrentContext();
                requestContext.addZuulRequestHeader(HttpHeaders.AUTHORIZATION,internalToken);
            }

        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.format("authentication failed: %s", e.getMessage()));
            return;
        }


        filterChain.doFilter(request, response);

    }

    public String jwtTokenGenerateInternalToken(String token) {

        User user = userClient.verifyToken(token);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()));

        String internalToken = String.format("%s:%s",user.getId(),user.getName());
        return internalToken;
    }

}
