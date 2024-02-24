package com.chukapoka.server.tree.service;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.tree.dto.TreeList;
import com.chukapoka.server.tree.dto.TreeListResponseDto;
import com.chukapoka.server.tree.dto.TreeRequestDto;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TreeServiceImpl implements TreeService{

    private final TreeRepository treeRepository;


    /** 트리생성 */
    @Override
    @Transactional
    public Tree createTree(TreeRequestDto treeRequestDto) {
        long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        // 클라이언트에서 입력 받을 필요없이 토큰으로 접속후 권한id로 셋팅
        treeRequestDto.setUpdatedBy(userId);
        Tree tree = new Tree();
        BeanUtils.copyProperties(treeRequestDto, tree); // mapper대신 사용할수 있지만 복사할 속성의 수가 적고 속성 이름이 일치하는 경우에 적합
        return treeRepository.save(tree);
    }

    /** 사용자 트리 리스트 조회(리스트용 모델) */
    @Override
    public TreeListResponseDto treeList() {
        List<TreeList> trees = treeRepository.findAllTrees();
        if (trees.isEmpty()) {
            return new TreeListResponseDto(new ArrayList<>());
        }
        return new TreeListResponseDto(trees);
    }

    /** 트리 상세 정보 조회 (상세정보 모델) */
    @Override
    public Tree treeDetail(Long treeId) {
        return null;
    }

    /** 트리 삭제 */
    @Override
    @Transactional
    public void deleteTree(Long treeId) {
        treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + treeId + "입니다."));
        treeRepository.deleteById(treeId);
    }



}
