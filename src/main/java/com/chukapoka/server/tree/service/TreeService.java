package com.chukapoka.server.tree.service;

import com.chukapoka.server.tree.dto.TreeDetailResponseDto;
import com.chukapoka.server.tree.dto.TreeListResponseDto;
import com.chukapoka.server.tree.dto.TreeCreateRequestDto;
import com.chukapoka.server.tree.dto.TreeModifyRequestDto;
import com.chukapoka.server.tree.entity.Tree;

public interface TreeService {

    /** 트리 저장 */
    Long createTree(TreeCreateRequestDto treeRequestDto);

    /** 트리리스트 조회(리스트용 모델) */
    TreeListResponseDto treeList();

    /** 트리 상세 정보 조회 (상세정보 모델) */
    TreeDetailResponseDto treeDetail(Long treeId);

    /** 트리 수정 */
    Tree treeModify(Long treeId, TreeModifyRequestDto treeModifyDto);

    /** 트리 삭제 */
    void treeDelete(Long treeId);

}
