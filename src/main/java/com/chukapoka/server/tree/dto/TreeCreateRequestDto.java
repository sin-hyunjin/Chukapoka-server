package com.chukapoka.server.tree.dto;

import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.TreeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TreeCreateRequestDto {
    @NotBlank(message = "title is null")
    private String title;
    @NotNull(message = "treeType is null")
    @ValidEnum(enumClass = TreeType.class, message = "TreeType must be MINE or NOT_YET_SEND")
    private String type;
    @NotBlank(message = "linkId is null")
    private String linkId;
    @NotBlank(message = "sendId is null")
    private String sendId;
    private Long updatedBy;  // 클라이언트에서는 입력받을 필요없음 ( TreeServicelmpl.createTree 에서 처리 )
    private String treeBgColor;
    private String groundColor;
    private String treeTopColor;
    private String treeItemColor;
    private String treeBottomColor;
}