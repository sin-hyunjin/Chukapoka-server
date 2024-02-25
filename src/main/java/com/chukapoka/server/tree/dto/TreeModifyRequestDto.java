package com.chukapoka.server.tree.dto;

import lombok.Data;

@Data
public class TreeModifyRequestDto {

    /** 트리에 관련된 것만 수정할것인지 ?*/
    private String title;
    private String type;
    private String treeBgColor;
    private String groundColor;
    private String treeTopColor;
    private String treeItemColor;
    private String treeBottomColor;
    
}
