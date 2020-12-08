package com.example.demo.role;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, FREELANCER;

    @Override
    public String getAuthority() {
        return name();
    }


}
