package com.chukapoka.server.tree.dto;

import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.TreeType;
import com.chukapoka.server.tree.entity.Tree;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TreeModifyRequestDto {

    /** 트리에 관련된 것만 수정할것인지 ?*/
    private String title;
    @NotBlank(message = "treeType is null")
    @ValidEnum(enumClass = TreeType.class, message = "TreeType must be MINE or NOT_YET_SEND")
    private String type;
    private String treeBgColor;
    private String groundColor;
    private String treeTopColor;
    private String treeItemColor;
    private String treeBottomColor;

    public void toEntity(Tree tree) {
        this.title = tree.getTitle();
        this.type = tree.getType();
        this.treeBgColor = tree.getTreeBgColor();
        this.groundColor = tree.getGroundColor();
        this.treeTopColor = tree.getTreeTopColor();
        this.treeItemColor = tree.getTreeItemColor();
        this.treeBottomColor = tree.getTreeBottomColor();
    }
}
