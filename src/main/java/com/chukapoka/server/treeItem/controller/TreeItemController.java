package com.chukapoka.server.treeItem.controller;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.treeItem.dto.TreeItemCreateRequestDto;
import com.chukapoka.server.treeItem.dto.TreeItemDetailResponseDto;
import com.chukapoka.server.treeItem.entity.TreeItem;
import com.chukapoka.server.treeItem.service.TreeItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/treeItem")
public class TreeItemController {

    private final TreeItemService treeItemService;
    /** 트리아이템 생성 */
    @PostMapping
    public BaseResponse<TreeItemDetailResponseDto> createTreeItem(@Valid @RequestBody TreeItemCreateRequestDto treeItemCreateRequestDto) {
        TreeItemDetailResponseDto responseDto = treeItemService.createTreeItem(treeItemCreateRequestDto);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }
}
