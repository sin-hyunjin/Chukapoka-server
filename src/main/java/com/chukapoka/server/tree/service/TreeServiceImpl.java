package com.chukapoka.server.tree.service;


import com.chukapoka.server.tree.dto.*;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import com.chukapoka.server.treeItem.entity.TreeItem;
import com.chukapoka.server.treeItem.repository.TreeItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public TreeDetailResponseDto createTree(TreeCreateRequestDto treeRequestDto, long userId) {
        // 클라이언트에서 입력 받을 필요없이 토큰으로 접속후 권한id로 셋팅
        Tree tree = treeRequestDto.toEntity(treeRequestDto, userId);
        treeRepository.save(tree);
        return new TreeDetailResponseDto(tree);
    }

    /** 사용자 트리 리스트 조회(리스트용 모델) */
    @Override
    public TreeListResponseDto treeList(long userId) {
        // modelMapper로 리스트 조회후 맵핑하는방법
        List<Tree> trees = treeRepository.findAllByUpdatedBy(userId);
        return new TreeListResponseDto(trees.stream().map(TreeDetailResponseDto::new).toList());
    }

    /** 트리 상세 정보 조회 (상세정보 모델) */
    @Override
    public TreeDetailResponseDto treeDetail(String treeId, long userId) {
        Tree tree = findTreeByIdOrThrow(treeId);
        // 트리에 속한 모든 TreeItem을 가져오기
        List<TreeItem> treeItems = treeItemRepository.findByTreeId(tree.getTreeId());
        // 트리와 트리아이템 전체목록 반환
        return new TreeDetailResponseDto(tree, treeItems.stream().map(item -> item.toTreeDetailTreeItemResponseDto(userId)).toList(), userId);
    }

    /** 트리 상세 정보 조회 (상세정보 모델) by linkId*/
    @Override
    public TreeDetailResponseDto treeDetailByLinkId(String linkId, long userId) {
        Tree tree = findTreeByLinkIdOrThrow(linkId);
        // 트리에 속한 모든 TreeItem을 가져오기
        List<TreeItem> treeItems = treeItemRepository.findByTreeId(tree.getTreeId());
        // 트리와 트리아이템 전체목록 반환
        return new TreeDetailResponseDto(tree, treeItems.stream().map(item -> item.toTreeDetailTreeItemResponseDto(userId)).toList());
    }

    /** 트리수정 */
    @Override
    @Transactional
    public TreeDetailResponseDto treeModify(String treeId, long userId, TreeModifyRequestDto treeModifyDto) {
        // 트리 아이디로 트리를 찾음
        Tree tree = findByTreeIdAndUpdatedByOrThrow(treeId, userId);
        // 새로운 트리 객체
        Tree newTree = treeModifyDto.toEntity(tree);
        // 변경된 트리 저장
        treeRepository.save(newTree);
        // 변경된 트리 상세 정보 반환
        return modelMapper.map(newTree, TreeDetailResponseDto.class);
    }

    @Override
    public TreeDetailResponseDto treeAccess(String sendId, long userId) {
        Tree tree = findTreeBySendIdOrThrow(sendId);
        // 현재 userId로 소유권 변경
        Tree newTree = tree.changeOwner(userId);
        treeRepository.save(newTree);
        return modelMapper.map(newTree, TreeDetailResponseDto.class);
    }

    /** 트리 삭제 */
    @Override
    @Transactional
    public void treeDelete(String treeId, long userId) {
        Tree tree = findByTreeIdAndUpdatedByOrThrow(treeId, userId);
        treeRepository.delete(tree);
    }


    /** treeId Exception 처리 메서드 */
    private Tree findTreeByIdOrThrow(String treeId) {
        return treeRepository.findByTreeId(treeId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + treeId + "입니다."));
    }

    private Tree findByTreeIdAndUpdatedByOrThrow(String treeId, long userId) {
        return treeRepository.findByTreeIdAndUpdatedBy(treeId, userId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + treeId + "입니다."));
    }

    /** treeId Exception 처리 메서드 by linkId */
    private Tree findTreeByLinkIdOrThrow(String linkId) {
        return treeRepository.findByLinkId(linkId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + linkId + "입니다."));
    }

    /** treeId Exception 처리 메서드 by sendId */
    private Tree findTreeBySendIdOrThrow(String sendId) {
        return treeRepository.findBySendId(sendId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + sendId + "입니다."));
    }


}
