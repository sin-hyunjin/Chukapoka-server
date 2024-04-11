package com.chukapoka.server.tree.repository;

import com.chukapoka.server.tree.entity.Tree;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreeRepository extends JpaRepository<Tree, String> {
    Optional<Tree> findByTreeId(String treeId);
    Optional<Tree> findByTreeIdAndUpdatedBy(String treeId, long userid);
    Optional<Tree> findByLinkId(String linkId);
    List<Tree> findAllByUpdatedBy(Long updatedBy);
}
