package com.chukapoka.server.treeItem.controller;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.treeItem.entity.TreeItem;
import com.chukapoka.server.treeItem.service.TreeItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/treeItem")
public class TreeItemController {

    private final TreeItemService treeItemService;
    @PostMapping
    public BaseResponse<TreeItem> createTreeItem(@RequestParam("treeId") Long treeId) {
        TreeItem responseDto = treeItemService.createTreeItem(treeId);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }
}
