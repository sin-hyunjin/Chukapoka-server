package com.chukapoka.server.tree.service;

import com.chukapoka.server.tree.dto.TreeListResponseDto;
import com.chukapoka.server.tree.dto.TreeRequestDto;
import com.chukapoka.server.tree.dto.TreeModifyRequestDto;
import com.chukapoka.server.tree.entity.Tree;

public interface TreeService {

    /** 트리 저장 */
    Tree createTree(TreeRequestDto treeRequestDto);

    /** 트리리스트 조회(리스트용 모델) */
    TreeListResponseDto treeList();

    /** 트리 상세 정보 조회 (상세정보 모델) */
    Tree treeDetail(Long treeId);

    /** 트리 수정 */
    Tree treeModify(Long treeId, TreeModifyRequestDto treeModifyDto);

    /** 트리 삭제 */
    void deleteTree(Long treeId);

}
