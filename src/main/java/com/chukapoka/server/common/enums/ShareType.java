package com.chukapoka.server.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ShareType {
    ONLY("ONLY"), // 내트리
    TOGETHER("TOGETHER"); // 미부여 트리

    private final String value;
    private static final Map<String, ShareType> lookup = new HashMap<>();

    static {
        for (ShareType ownerType : ShareType.values()) {
            lookup.put(ownerType.getValue(), ownerType);
        }
    }

    ShareType(String description) {
        this.value = description;
    }

    public static ShareType getByValue(String value) {
        return lookup.get(value);
    }

}

