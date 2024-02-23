package com.chukapoka.server.tree.controller;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.service.TreeService;
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
    public BaseResponse<Tree>createTree(@RequestBody Tree tree) {
        Tree response = treeService.createTree(tree);
        return new BaseResponse<>(ResultType.SUCCESS, response);
    }

    /** 트리리스트 목록 */
    @GetMapping
    private BaseResponse<List<Tree>> treeList(@RequestParam("id") Long userId) {
        List<Tree> response = treeService.treeList(userId);
        return new BaseResponse<>(ResultType.SUCCESS, response);
    }

    /** 트리 삭제 */
    @DeleteMapping
    private BaseResponse<Void> deleteTree(@RequestParam("treeId") Long treeId) {
        treeService.deleteTree(treeId);
        return new BaseResponse<>(ResultType.SUCCESS, null);
    }
}

