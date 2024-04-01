package com.chukapoka.server.treeItem.dto;

import com.chukapoka.server.treeItem.entity.TreeItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeItemDetailResponseDto {

    /** 트리 아이템 상세정보 */
    private String treeItemId;  // tree item id
    private String treeId;
    private String title;
    private String content;
    private String bgType;
    private LocalDateTime updatedAt;

    public TreeItemDetailResponseDto(TreeItem treeItem) {
        this.treeItemId = treeItem.getId();
        this.treeId = treeItem.getTreeId();
        this.title = treeItem.getTitle();
        this.content = treeItem.getContent();
        this.bgType = treeItem.getBgType();
        this.updatedAt = treeItem.getUpdatedAt();
    }
}
