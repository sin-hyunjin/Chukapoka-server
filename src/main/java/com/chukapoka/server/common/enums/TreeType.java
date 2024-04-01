package com.chukapoka.server.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TreeType {
    TREE_TYPE_01("TREE_TYPE_01"),
    TREE_TYPE_02("TREE_TYPE_02"),
    TREE_TYPE_03("TREE_TYPE_03"),
    TREE_TYPE_04("TREE_TYPE_04");

    private final String description;
    private static final Map<String, TreeType> lookup = new HashMap<>();

    static {
        for (TreeType ownerType : TreeType.values()) {
            lookup.put(ownerType.getDescription(), ownerType);
        }
    }

    TreeType(String description) {
        this.description = description;
    }

    public static TreeType getByDescription(String description) {
        return lookup.get(description);
    }

}
