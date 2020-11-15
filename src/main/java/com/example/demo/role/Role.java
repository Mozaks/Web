package com.example.demo.role;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, EMPLOYER;

    @Override
    public String getAuthority() {
        return name();
    }


}
