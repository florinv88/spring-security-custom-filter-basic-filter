package org.fnkcode.springsecc4.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fnkcode.springsecc4.config.authentications.ApiKeyAuthentication;
import org.fnkcode.springsecc4.config.managers.AuthManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ApiKeyFilters extends OncePerRequestFilter {

    private final AuthManager authManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // create auth objected - not authenticated
        // delegate to the manager to authenticate
        // if everything it s ok , move to the next filter

        var requestApiKey = String.valueOf(request.getHeader("KEY"));

        //IF we don't have an api key then BasicAuth should be called
        if (requestApiKey ==null || requestApiKey.equals("null")) {
            filterChain.doFilter(request,response);
        }

        var auth = new ApiKeyAuthentication(requestApiKey);
        auth.setAuthenticated(false);


        try {
            var authObjFromManager = authManager.authenticate(auth);
            if (authObjFromManager.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authObjFromManager);
                filterChain.doFilter(request,response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
