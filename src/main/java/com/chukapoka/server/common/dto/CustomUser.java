package com.chukapoka.server.common.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    private final Long userId;

    public CustomUser(Long userId, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
