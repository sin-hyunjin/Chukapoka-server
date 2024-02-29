package com.chukapoka.server.treeItem.service;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import com.chukapoka.server.treeItem.dto.TreeItemCreateRequestDto;
import com.chukapoka.server.treeItem.dto.TreeItemDetailResponseDto;
import com.chukapoka.server.treeItem.entity.TreeItem;
import com.chukapoka.server.treeItem.repository.TreeItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}



