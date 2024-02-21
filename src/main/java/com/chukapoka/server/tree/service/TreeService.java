package com.chukapoka.server.tree.service;

import com.chukapoka.server.tree.entity.Tree;

import java.util.List;

public interface TreeService {

    // 회원 아이디로 트리 아이템 저장
    Tree createTree( Tree tree);

    // 트리 리스트 조회
    List<Tree> TreeList();

    // 트리 삭제
    void deleteTree(String treeId);
}
