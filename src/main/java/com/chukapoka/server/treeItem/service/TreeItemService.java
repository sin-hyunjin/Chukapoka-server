package com.chukapoka.server.treeItem.service;
import com.chukapoka.server.treeItem.dto.TreeItemCreateRequestDto;
import com.chukapoka.server.treeItem.dto.TreeItemDetailResponseDto;

public interface TreeItemService {

    /** 트리 아이템 생성 */
    TreeItemDetailResponseDto createTreeItem(TreeItemCreateRequestDto treeItemCreateRequestDto);

}
