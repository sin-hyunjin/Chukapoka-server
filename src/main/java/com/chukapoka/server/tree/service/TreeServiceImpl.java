package com.chukapoka.server.tree.service;

import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TreeServiceImpl implements TreeService{

    private final TreeRepository treeRepository;

    /** 트리생성 */
    @Override
    public Tree createTree(Tree tree) {
        return null;
    }

    /** 사용자 트리 리스트 */
    @Override
    public List<Tree> TreeList() {
        return null;
    }

    /** 트리 삭제 */
    @Override
    public void deleteTree(String treeId) {

    }
}
