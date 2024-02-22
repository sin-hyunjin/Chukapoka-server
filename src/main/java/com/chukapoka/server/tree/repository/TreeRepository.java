package com.chukapoka.server.tree.repository;

import com.chukapoka.server.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeRepository extends JpaRepository<Tree, String> {
    Tree findById(Long treeId);
    void delete(Long treeId);
}
