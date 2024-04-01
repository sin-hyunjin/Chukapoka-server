package com.chukapoka.server.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum OwnerType {
    MINE("MINE"), // 내트리
    NOT_YET_SEND("NOT_YET_SEND"); // 미부여 트리

    private final String value;
    private static final Map<String, OwnerType> lookup = new HashMap<>();

    static {
        for (OwnerType ownerType : OwnerType.values()) {
            lookup.put(ownerType.getValue(), ownerType);
        }
    }

    OwnerType(String description) {
        this.value = description;
    }

    public static OwnerType getByValue(String value) {
        return lookup.get(value);
    }

}

