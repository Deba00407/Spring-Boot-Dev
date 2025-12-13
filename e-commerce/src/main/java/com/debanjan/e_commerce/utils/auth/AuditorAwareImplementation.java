package com.debanjan.e_commerce.utils.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImplementation implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO -> get current user from spring security

        return Optional.of("current-test-admin");
    }
}
