package com.chukapoka.server.tree.service;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.tree.dto.*;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import com.chukapoka.server.treeItem.entity.TreeItem;
import com.chukapoka.server.treeItem.repository.TreeItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class TreeServiceImpl implements TreeService{

    private final TreeRepository treeRepository;
    private final TreeItemRepository treeItemRepository;
    private final ModelMapper modelMapper;

    /** 트리생성 */
    @Override
    @Transactional
    public TreeDetailResponseDto createTree(TreeCreateRequestDto treeRequestDto) {
        // 클라이언트에서 입력 받을 필요없이 토큰으로 접속후 권한id로 셋팅
        long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        Tree tree = new Tree();
        treeRequestDto.setUpdatedBy(userId);

//        BeanUtils.copyProperties(treeRequestDto, tree); // mapper대신 사용할수 있지만 복사할 속성의 수가 적고 속성 이름이 일치하는 경우에 적합
        modelMapper.map(treeRequestDto, tree);
        treeRepository.save(tree);
        return modelMapper.map(tree, TreeDetailResponseDto.class);
    }

    /** 사용자 트리 리스트 조회(리스트용 모델) */
    @Override
    public TreeListResponseDto treeList() {
        List<TreeList> trees = treeRepository.findAllTrees();
        return new TreeListResponseDto(trees);
    }

    /** 트리 상세 정보 조회 (상세정보 모델) */
    @Override
    public TreeDetailResponseDto treeDetail(String treeId) {
        Tree tree = findTreeByIdOrThrow(treeId);
        // 트리에 속한 모든 TreeItem을 가져오기
        List<TreeItem> treeItems = treeItemRepository.findByTreeId(tree.getTreeId());
        TreeDetailResponseDto treeDetailResponseDto = modelMapper.map(tree, TreeDetailResponseDto.class);
        treeDetailResponseDto.setTreeItem(treeItems);
        return treeDetailResponseDto;

    }

    /** 트리수정 */
    @Override
    @Transactional
    public TreeDetailResponseDto treeModify(String treeId, TreeModifyRequestDto treeModifyDto) {
        // 트리 아이디로 트리를 찾음
        Tree tree = findTreeByIdOrThrow(treeId);
        modelMapper.map(treeModifyDto, tree);
        // 변경된 트리 저장
        treeRepository.save(tree);
        // 변경된 트리 상세 정보 반환
        return modelMapper.map(tree, TreeDetailResponseDto.class);
    }

    /** 트리 삭제 */
    @Override
    @Transactional
    public void treeDelete(String treeId) {
        findTreeByIdOrThrow(treeId);
        treeRepository.deleteById(treeId);
    }

    /** treeId Exception 처리 메서드 */
    private Tree findTreeByIdOrThrow(String treeId) {
        return treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + treeId + "입니다."));
    }
}
