package com.chukapoka.server.tree.dto;

import lombok.Data;

@Data
public class TreeModifyRequestDto {
    private String title;
    private String type;
    private String treeBgColor;
    private String groundColor;
    private String treeTopColor;
    private String treeItemColor;
    private String treeBottomColor;
}
