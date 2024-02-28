package com.chukapoka.server.treeItem.service;

import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.tree.repository.TreeRepository;
import com.chukapoka.server.tree.service.TreeService;
import com.chukapoka.server.treeItem.entity.TreeItem;
import com.chukapoka.server.treeItem.repository.TreeItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TreeItemServiceImpl implements TreeItemService{

    private final TreeItemRepository treeItemRepository;
    private final TreeRepository treeRepository;
    @Override
    public TreeItem createTreeItem(Long treeId) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found with id: " + treeId));

        TreeItem treeItem = new TreeItem();
        treeItem.setTreeId(treeId);

        return treeItemRepository.save(treeItem);
    }
}
