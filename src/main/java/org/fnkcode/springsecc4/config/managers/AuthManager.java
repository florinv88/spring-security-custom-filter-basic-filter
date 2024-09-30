package org.fnkcode.springsecc4.config.managers;

import org.fnkcode.springsecc4.config.providers.CustomProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class AuthManager implements AuthenticationManager {

    @Value("${api.secret}")
    private String secretKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var customProvider = new CustomProvider(secretKey);

        if (customProvider.supports(authentication.getClass())){
            return customProvider.authenticate(authentication);
        }
        return authentication;

    }
}
