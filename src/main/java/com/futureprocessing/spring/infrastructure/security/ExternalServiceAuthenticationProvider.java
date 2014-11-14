package com.futureprocessing.spring.infrastructure.security;

public interface ExternalServiceAuthenticationProvider {

    AuthenticationWithToken authenticate(String username, String password);
}
