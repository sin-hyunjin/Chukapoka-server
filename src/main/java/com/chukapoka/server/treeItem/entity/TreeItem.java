package com.chukapoka.server.treeItem.entity;


import com.chukapoka.server.treeItem.dto.TreeDetailTreeItemResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate // 데이터의 변경사항이 있는 것만 수정
@Table(name = "tb_treeItem")
public class TreeItem {
    @Id
    @Column(name = "treeItemId", unique = true, nullable = false)
    private String id;

    @Column(name = "treeId")
    private String treeId;

    /** 편지 제목 */
    @Column(name = "title")
    private String title;

    /** 편지 내용*/
    @Column(name = "content")
    private String content;

    @Column(name = "bgType")
    private String bgType;

    /** userId가 값임 */
    @Column(name = "updatedBy")
    private Long updatedBy;

    /** 생성 시간 */
    @Column(name = "updatedAt", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist // JPA에서는 엔티티의 생명주기 중 하나의 이벤트에 대해 하나의 @PrePersist 메서드만을 허용
    public void prePersist() {
        this.updatedAt = LocalDateTime.now();
        if (this.id == null) {
            this.id = TreeItemId(this.updatedBy, this.updatedAt);
        }
    }
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static String TreeItemId(long updatedBy, LocalDateTime updatedAt) {
        UUID randomUUID = UUID.randomUUID();
        return Base64.getEncoder().encodeToString(("treeItemId" + updatedBy + updatedAt + randomUUID).getBytes());
    }

    public TreeDetailTreeItemResponseDto toTreeDetailTreeItemResponseDto(long userId) {
        return new TreeDetailTreeItemResponseDto(
                id,
                treeId,
                title,
                updatedAt,
                updatedBy == userId
        );
    }

}
