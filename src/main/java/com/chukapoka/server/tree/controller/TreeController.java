package com.chukapoka.server.tree.controller;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.tree.dto.TreeListResponseDto;
import com.chukapoka.server.tree.dto.TreeRequestDto;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.service.TreeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private final TreeService treeService ;

    /**트리 생성 */
    @PostMapping
    public BaseResponse<Tree>createTree(@Valid @RequestBody TreeRequestDto treeRequestDto) {
        Tree responseDto = treeService.createTree(treeRequestDto);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    /** 트리리스트 목록 */
    @GetMapping
    public BaseResponse<TreeListResponseDto> treeList() {
        TreeListResponseDto responseDto = treeService.treeList();
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }


    /** 트리 삭제 */
    @DeleteMapping("/{treeId}")
    public BaseResponse<Void> deleteTree(@PathVariable("treeId") Long treeId) {
        treeService.deleteTree(treeId);
        return new BaseResponse<>(ResultType.SUCCESS, null);
    }
}

