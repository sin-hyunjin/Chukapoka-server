package com.chukapoka.server.treeItem.service;


import com.chukapoka.server.treeItem.entity.TreeItem;
import org.springframework.stereotype.Service;


public interface TreeItemService {

    TreeItem createTreeItem(Long treeId);

}
