package com.chukapoka.server.tree.service;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.tree.dto.*;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TreeServiceImpl implements TreeService{

    private final TreeRepository treeRepository;
    private final ModelMapper modelMapper;

    /** 트리생성 */
    @Override
    @Transactional
    public Tree createTree(TreeCreateRequestDto treeRequestDto) {
        Tree tree = new Tree();
        // 클라이언트에서 입력 받을 필요없이 토큰으로 접속후 권한id로 셋팅
        long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        treeRequestDto.setUpdatedBy(userId);

        // 질문 : 여기를 build를 사용하는게 좋을지 modelMapper를 사용하는게 좋을지??
        // -> 서버에서 linkId, sendId를 만들어줄꺼라면 build가 좋을꺼같은데...
        BeanUtils.copyProperties(treeRequestDto, tree); // mapper대신 사용할수 있지만 복사할 속성의 수가 적고 속성 이름이 일치하는 경우에 적합
        return treeRepository.save(tree);
        
    }

    /** 사용자 트리 리스트 조회(리스트용 모델) */
    @Override
    public TreeListResponseDto treeList() {
        List<TreeList> trees = treeRepository.findAllTrees();
        return new TreeListResponseDto(trees);
    }

    /** 트리 상세 정보 조회 (상세정보 모델) */
    @Override
    public TreeDetailResponseDto treeDetail(Long treeId) {
        Tree tree = findTreeByIdOrThrow(treeId);
        // tree entity를 TreeDetailResponseDto로 매핑
        return modelMapper.map(tree, TreeDetailResponseDto.class);
    }

    /** 트리수정 */
    @Override
    public Tree treeModify(Long treeId, TreeModifyRequestDto treeModifyDto) {
        // 트리 아이디로 트리를 찾음
        Tree tree = findTreeByIdOrThrow(treeId);
        return treeUpdate(tree, treeModifyDto);
    }

    /** 트리 삭제 */
    @Override
    @Transactional
    public void treeDelete(Long treeId) {
        findTreeByIdOrThrow(treeId);
        treeRepository.deleteById(treeId);
    }



    /** 트리필드 정보 업데이트 메서드*/
    @Transactional // 데이터 변경감지
    private Tree treeUpdate(Tree tree, TreeModifyRequestDto treeModifyDto) {
        tree.setTitle(treeModifyDto.getTitle());
        tree.setType(treeModifyDto.getType());
        tree.setTreeBgColor(treeModifyDto.getTreeBgColor());
        tree.setGroundColor(treeModifyDto.getGroundColor());
        tree.setTreeTopColor(treeModifyDto.getTreeTopColor());
        tree.setTreeItemColor(treeModifyDto.getTreeItemColor());
        tree.setTreeBottomColor(treeModifyDto.getTreeBottomColor());
        return treeRepository.save(tree);
    }

    /** treeId Exception 처리 메서드 */
    private Tree findTreeByIdOrThrow(Long treeId) {
        return treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + treeId + "입니다."));
    }
}
