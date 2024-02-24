package com.chukapoka.server.tree.repository;


import com.chukapoka.server.tree.dto.TreeList;
import com.chukapoka.server.tree.entity.Tree;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
    Optional<Tree> findById(Long treeId);
    @Query("SELECT new com.chukapoka.server.tree.dto.TreeList(tree.treeId, tree.title, tree.type, tree.linkId, tree.sendId, tree.updatedBy, tree.updatedAt) FROM Tree tree")
    List<TreeList> findAllTrees();


}
