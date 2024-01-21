package com.chukapoka.server.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Getter
public enum NextActionType {
    LOGIN("LOGIN"),
    JOIN("JOIN");

    private final String value;
    private static final Map<String, NextActionType> lookup = new HashMap<>();

    static {
        for (NextActionType actionType : NextActionType.values()) {
            lookup.put(actionType.getValue(), actionType);
        }
    }

    NextActionType(String value) {
        this.value = value;
    }

    public static NextActionType getByValue(String value) {
        return lookup.get(value);
    }
}

