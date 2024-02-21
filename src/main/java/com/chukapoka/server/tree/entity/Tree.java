package com.chukapoka.server.tree.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_tree")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treeId")
    private Long id;
    @Column(name = "linkId", nullable = false)
    private String linkId;
    @Column(name = "sendId", nullable = false)
    private String sendId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "updateAt", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Tree(Long id, String linkId, String sendId, String title, LocalDateTime updatedAt) {
        this.id = id;
        this.linkId = linkId;
        this.sendId = sendId;
        this.title = title;
        this.updatedAt = updatedAt;
    }
    //    private String type;
//    private String treeBgColor;
//    private String groundColor;
//    private String treeTopColor;
//    private String treeItemColor;
//    private String treeBottomColor;

}
