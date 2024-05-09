package com.project.tj.com.project.tj.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.project.tj.com.project.tj.enums.ApiKeyandId.API_KEY_MYPROJECT;


@Component
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-Key";
    private static final String API_ID_HEADER = "X-API-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean valid = validateApiKeyAndId(request.getHeader(API_KEY_HEADER), request.getHeader(API_ID_HEADER));


        if (valid) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid API key or ID");
        }
    }

    private boolean validateApiKeyAndId(String apiKey, String apiId) {
        return apiKey.equals(API_KEY_MYPROJECT.getApiKey()) && apiId.equals(API_KEY_MYPROJECT.getId());
    }

}
