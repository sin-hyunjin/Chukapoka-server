package com.chukapoka.server.treeItem.dto;

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
    @NotBlank(message = "treeItemColor is null")
    private String treeItemColor;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}


