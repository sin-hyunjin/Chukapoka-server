package com.chukapoka.server.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Authority {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String authority;
    private static final Map<String, Authority> lookup = new HashMap<>();

    static {
        for (Authority authority : Authority.values()) {
            lookup.put(authority.getAuthority(), authority);
        }
    }

    Authority(String authority) {
        this.authority = authority;
    }

    public static Authority getByAuthority(String authority) {
        return lookup.get(authority);
    }
}