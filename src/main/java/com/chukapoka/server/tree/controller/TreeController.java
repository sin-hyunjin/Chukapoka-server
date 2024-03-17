package com.chukapoka.server.tree.controller;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.dto.CustomUserDetails;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.tree.dto.TreeDetailResponseDto;
import com.chukapoka.server.tree.dto.TreeListResponseDto;
import com.chukapoka.server.tree.dto.TreeCreateRequestDto;
import com.chukapoka.server.tree.dto.TreeModifyRequestDto;
import com.chukapoka.server.tree.service.TreeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private final TreeService treeService ;

    /**트리 생성 */
    @PostMapping
    public BaseResponse<TreeDetailResponseDto>createTree(@Valid @RequestBody TreeCreateRequestDto treeRequestDto) {
        long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        TreeDetailResponseDto responseDto = treeService.createTree(treeRequestDto, userId);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    /** 트리리스트 목록 */
    @GetMapping("/list/{updatedBy}")
    public BaseResponse<TreeListResponseDto> treeList(@PathVariable("updatedBy") Long updatedBy) {
        TreeListResponseDto responseDto = treeService.treeList(updatedBy);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    /** 트리상세 정보 */
    @GetMapping("/{treeId}/{updatedBy}")
    private BaseResponse<TreeDetailResponseDto> treeDetail(@PathVariable("treeId") String treeId,
                                                           @PathVariable("updatedBy") Long updatedBy) {
        TreeDetailResponseDto responseDto = treeService.treeDetail(treeId, updatedBy);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    /** 트리 수정 */
    @PutMapping("/{treeId}/{updatedBy}")
    public BaseResponse<TreeDetailResponseDto> treeModify(@PathVariable("treeId") String treeId,
                                                          @PathVariable("updatedBy") Long updatedBy,
                                                          @Valid @RequestBody TreeModifyRequestDto treeModifyDto) {
        TreeDetailResponseDto responseDto = treeService.treeModify(treeId,updatedBy,treeModifyDto);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }


    /** 트리 삭제 */
    @DeleteMapping("/{treeId}/{updatedBy}")
    public BaseResponse<Void> treeDelete(@PathVariable("treeId") String treeId,
                                         @PathVariable("updatedBy") Long updatedBy) {
        treeService.treeDelete(treeId,updatedBy);
        return new BaseResponse<>(ResultType.SUCCESS, null);
    }


}

