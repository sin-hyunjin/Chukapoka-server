package com.chukapoka.server.treeItem.dto;

import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.BgType;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.treeItem.entity.TreeItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TreeItemCreateRequestDto {

    @NotNull(message = "treeId is null")
    private String treeId;
    @NotBlank(message = "title is null")
    private String title;
    @NotBlank(message = "content is null")
    private String content;
    @ValidEnum(enumClass = BgType.class, message = "bgType must be BG_TYPE_")
    private String bgType;


    public TreeItem toEntity(TreeItemCreateRequestDto treeItemCreateRequestDto, long userId) {
        return TreeItem.builder()
                .treeId(treeItemCreateRequestDto.getTreeId())
                .title(treeItemCreateRequestDto.getTitle())
                .content(treeItemCreateRequestDto.getContent())
                .bgType(treeItemCreateRequestDto.getBgType())
                .updatedBy(userId)
                .updatedAt(LocalDateTime.now())
                .build();
    }

}


