package com.futureprocessing.spring.infrastructure;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedExternalServiceProvider {

    public AuthenticatedExternalWebService provide() {
        return (AuthenticatedExternalWebService) SecurityContextHolder.getContext().getAuthentication();
    }
}
