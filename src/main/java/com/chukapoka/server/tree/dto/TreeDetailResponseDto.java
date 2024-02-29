package com.chukapoka.server.tree.dto;

import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.treeItem.entity.TreeItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeDetailResponseDto {

    /** 트리상세 정보 */
    private String treeId;
    private String title;
    private String type; // MINE or NOT_YEN_SEND
    private String linkId;
    private String sendId;
    private String treeBgColor;
    private String groundColor;
    private String treeTopColor;
    private String treeItemColor;
    private String treeBottomColor;
    private Long updatedBy;
    private LocalDateTime updatedAt;

    /** treeItem 목록 */
    private List<TreeItem> treeItem;


}
