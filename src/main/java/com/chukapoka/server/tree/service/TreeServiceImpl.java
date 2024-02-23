package com.chukapoka.server.tree.service;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TreeServiceImpl implements TreeService{

    private final TreeRepository treeRepository;

    /** 트리생성 */
    @Override
    public Tree createTree(Tree tree) {
        long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        tree.setUpdatedBy(userId);
        return treeRepository.save(tree);
    }

    /**사용자 트리 리스트 조화(리스트용 모델) */
    @Override
    public List<Tree> treeList(Long userId) {
        return treeRepository.findByUpdatedBy(userId);
    }

    /** 트리 상세 정보 조회 (상세정보 모델) */
    @Override
    public Tree treeDetail(Long treeId) {
        return null;
    }

    /** 트리 삭제 */
    @Override
    public void deleteTree(Long treeId) {
        treeRepository.deleteById(treeId);
    }



}
