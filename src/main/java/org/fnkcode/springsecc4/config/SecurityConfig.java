package org.fnkcode.springsecc4.config;

import lombok.RequiredArgsConstructor;
import org.fnkcode.springsecc4.config.filters.ApiKeyFilters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiKeyFilters apiKeyFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(apiKeyFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(req->req
                        .anyRequest()
                        .authenticated())
                .build();
    }

}
