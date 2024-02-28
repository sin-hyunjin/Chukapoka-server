package com.chukapoka.server.treeItem.repository;

import com.chukapoka.server.treeItem.entity.TreeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeItemRepository extends JpaRepository<TreeItem, String> {

}
