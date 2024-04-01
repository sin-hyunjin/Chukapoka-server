package com.chukapoka.server.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum BgType {
    BG_TYPE_01("BG_TYPE_01"),
    BG_TYPE_02("BG_TYPE_02"),
    BG_TYPE_03("BG_TYPE_03"),
    BG_TYPE_04("BG_TYPE_04");

    private final String description;
    private static final Map<String, BgType> lookup = new HashMap<>();

    static {
        for (BgType ownerType : BgType.values()) {
            lookup.put(ownerType.getDescription(), ownerType);
        }
    }

    BgType(String description) {
        this.description = description;
    }

    public static BgType getByDescription(String description) {
        return lookup.get(description);
    }

}
