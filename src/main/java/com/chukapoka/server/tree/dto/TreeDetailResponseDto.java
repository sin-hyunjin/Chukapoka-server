package com.chukapoka.server.tree.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeDetailResponseDto {

    /** 트리상세 정보 */
    private Long treeId;
    private String title;
    private String type; // MINE or NOT_YEN_SEND
    private String treeBgColor;
    private String groundColor;
    private String treeTopColor;
    private String treeItemColor;
    private String treeBottomColor;

    /** treeItem 목록 필요 */

    private Long updatedBy;
    private LocalDateTime updatedAt;



}
