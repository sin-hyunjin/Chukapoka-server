package com.chukapoka.server.tree.service;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.tree.dto.TreeList;
import com.chukapoka.server.tree.dto.TreeListResponseDto;
import com.chukapoka.server.tree.dto.TreeRequestDto;
import com.chukapoka.server.tree.dto.TreeModifyRequestDto;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    /** 트리생성 */
    @Override
    @Transactional
    public Tree createTree(TreeRequestDto treeRequestDto) {
        Tree tree = new Tree();
        // 클라이언트에서 입력 받을 필요없이 토큰으로 접속후 권한id로 셋팅
        long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        treeRequestDto.setUpdatedBy(userId);
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

    /** 트리수정 */
    @Override
    @Transactional // 데이터 변경감지
    public Tree treeModify(Long treeId, TreeModifyRequestDto treeModifyDto) {
        // 트리 아이디로 트리를 찾음
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("treeId " + treeId + " 를 찾을 수 없습니다."));
        updateTreeAttributes(tree, treeModifyDto);
        return treeRepository.save(tree);

    }

    /** 트리 삭제 */
    @Override
    @Transactional
    public void deleteTree(Long treeId) {
        treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + treeId + "입니다."));
        treeRepository.deleteById(treeId);
    }


    /** 트리필드 정보 업데이트 */
    private void updateTreeAttributes(Tree tree, TreeModifyRequestDto treeModifyDto) {
        tree.setTitle(treeModifyDto.getTitle());
        tree.setType(treeModifyDto.getType());
        tree.setTreeBgColor(treeModifyDto.getTreeBgColor());
        tree.setGroundColor(treeModifyDto.getGroundColor());
        tree.setTreeTopColor(treeModifyDto.getTreeTopColor());
        tree.setTreeItemColor(treeModifyDto.getTreeItemColor());
        tree.setTreeBottomColor(treeModifyDto.getTreeBottomColor());
    }
}
