package com.chukapoka.server.treeItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeDetailTreeItemResponseDto {
    /** 트리 상세정보에 포함된 트리아이템 정보(content는 제외) */

    private String treeItemId;
    private String treeId;
    private String title;
    private LocalDateTime updatedAt;
    private boolean isMyTreeItem;
}
