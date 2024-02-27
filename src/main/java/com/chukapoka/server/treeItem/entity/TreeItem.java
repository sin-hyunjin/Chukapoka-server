package com.chukapoka.server.treeItem.entity;


import com.chukapoka.server.tree.entity.Tree;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate // 데이터의 변경사항이 있는 것만 수정
@Table(name = "tb_treeItem")
public class TreeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treeItemId")
    private Long treeItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treeId", referencedColumnName = "treeId")
    private Tree treeId;

    /** 편지 제목 */
    @Column(name = "title")
    private String title;

    /** 편지 내용*/
    @Column(name = "content")
    private String content;

    /** 트라 관련 색상은 String -> enum type으로 상수로 바꿔야 관리가 더 편할것같음 */
    @Column(name = "treeBgColor", nullable = true)
    private String treeBgColor;

    @Column(name = "groundColor", nullable = true)
    private String groundColor;

    @Column(name = "treeTopColor", nullable = true)
    private String treeTopColor;

    @Column(name = "treeItemColor", nullable = true)
    private String treeItemColor;

    @Column(name = "treeBottomColor", nullable = true)
    private String treeBottomColor;

    /** userId가 값임 */
    @Column(name = "updatedBy")
    private Long updatedBy;

    /** 생성 시간 */
    @Column(name = "updatedAt", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;



    @PrePersist
    public void updatedAt() {
        this.updatedAt = LocalDateTime.now();
    }


}
