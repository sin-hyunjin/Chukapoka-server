package com.chukapoka.server.treeItem.controller;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.treeItem.dto.TreeItemCreateRequestDto;
import com.chukapoka.server.treeItem.dto.TreeItemDetailResponseDto;
import com.chukapoka.server.treeItem.dto.TreeItemListResponseDto;
import com.chukapoka.server.treeItem.dto.TreeItemModifyRequestDto;
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

    /** 트리리스트 목록 */
    @GetMapping
    public BaseResponse<TreeItemListResponseDto> treeItemList() {
        TreeItemListResponseDto responseDto = treeItemService.treeList();
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    /** 트리상세 정보 */
    @GetMapping("/{treeItemId}")
    private BaseResponse<TreeItemDetailResponseDto> treeItemDetail(@PathVariable("treeItemId") String treeItemId) {
        TreeItemDetailResponseDto responseDto = treeItemService.treeDetail(treeItemId);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    /** 트리 수정 */
    @PutMapping("/{treeItemId}")
    public BaseResponse<TreeItemDetailResponseDto> treeItemModify(@PathVariable("treeItemId") String treeItemId,
                                                          @Valid @RequestBody TreeItemModifyRequestDto treeItemModifyDto) {
        TreeItemDetailResponseDto responseDto = treeItemService.treeModify(treeItemId, treeItemModifyDto);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }


    /** 트리 삭제 */
    @DeleteMapping("/{treeItemId}")
    public BaseResponse<Void> treeItemDelete(@PathVariable("treeItemId") String treeItemId) {
        treeItemService.treeItemDelete(treeItemId);
        return new BaseResponse<>(ResultType.SUCCESS, null);
    }
}
