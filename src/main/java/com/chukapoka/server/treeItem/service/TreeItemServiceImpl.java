package com.chukapoka.server.treeItem.service;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import com.chukapoka.server.treeItem.dto.TreeItemCreateRequestDto;
import com.chukapoka.server.treeItem.dto.TreeItemDetailResponseDto;
import com.chukapoka.server.treeItem.dto.TreeItemListResponseDto;
import com.chukapoka.server.treeItem.dto.TreeItemModifyRequestDto;
import com.chukapoka.server.treeItem.entity.TreeItem;
import com.chukapoka.server.treeItem.repository.TreeItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TreeItemServiceImpl implements TreeItemService{

    private final TreeItemRepository treeItemRepository;
    private final TreeRepository treeRepository;
    private final ModelMapper modelMapper;

    /** 트리 아이템 생성 */
    @Override
    @Transactional
    public TreeItemDetailResponseDto createTreeItem(TreeItemCreateRequestDto treeItemCreateRequestDto) {
        String treeId = treeItemCreateRequestDto.getTreeId();
        long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return saveTreeItem(treeId, userId, treeItemCreateRequestDto);
    }

    /** 트리아이템 (리스트) */
    @Override
    public TreeItemListResponseDto treeList() {
        List<TreeItem> treeItems = treeItemRepository.findAll();
        List<TreeItemDetailResponseDto> treeItemDetailResponseDtos = treeItems.stream()
                .map(treeItem -> modelMapper.map(treeItem, TreeItemDetailResponseDto.class))
                .collect(Collectors.toList());
        return new TreeItemListResponseDto(treeItemDetailResponseDtos);
//        return null;

    }

    /** 트라이이템 (상세정보) */
    @Override
    public TreeItemDetailResponseDto treeDetail(String treeItemId) {
        TreeItem treeItem = findTreeItemIdOrThrow(treeItemId);
        return modelMapper.map(treeItem, TreeItemDetailResponseDto.class);
    }

    /** 트리아이템 수정 */
    @Override
    @Transactional
    public TreeItemDetailResponseDto treeModify(String treeItemId, TreeItemModifyRequestDto treeItemModifyDto) {
        TreeItem treeItem = findTreeItemIdOrThrow(treeItemId);
        modelMapper.map(treeItemModifyDto, treeItem);
        treeItem.setUpdatedAt(LocalDateTime.now());
        // 변경된 트리아이템 저장
        treeItemRepository.save(treeItem);
        // 변경된 트리아이템 상세 정보 반환
        return modelMapper.map(treeItem, TreeItemDetailResponseDto.class);
    }

    /** 트리아이템 삭제 */
    @Override
    @Transactional
    public void treeItemDelete(String treeItemId) {
        findTreeItemIdOrThrow(treeItemId);
        treeItemRepository.deleteById(treeItemId);
    }


    /** 트라이이템 저장 메서드 */
    private TreeItemDetailResponseDto saveTreeItem(String treeId, long userId, TreeItemCreateRequestDto treeItemCreateRequestDto) {
        // 트리 객체 조회
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found with id: " + treeId));
        // 트리 아이템 생성 및 저장
        TreeItem treeItem = modelMapper.map(treeItemCreateRequestDto, TreeItem.class);
        treeItem.setTreeId(treeId);
        treeItem.setUpdatedBy(userId);
        treeItem.setUpdatedAt(LocalDateTime.now());
        treeItemRepository.save(treeItem);

        return modelMapper.map(treeItem, TreeItemDetailResponseDto.class);
    }


    /** treeItemId Exception 처리 메서드 */
    private TreeItem findTreeItemIdOrThrow(String treeItemId) {
        return treeItemRepository.findById(treeItemId)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 " + treeItemId + "입니다."));
    }
}





