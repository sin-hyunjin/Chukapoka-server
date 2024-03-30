package com.chukapoka.server.tree.dto;

import com.chukapoka.server.tree.entity.Tree;
import com.chukapoka.server.treeItem.dto.TreeDetailTreeItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeDetailResponseDto {

    /** 트리상세 정보 */
    private String treeId;
    private String title;
    private String linkId;
    private String sendId;
    private String ownerType;
    private String shareType;
    private String treeType;
    private String bgType;
    private LocalDateTime updatedAt;

//    /** treeItem 목록 */ TODO: 추후 추가 수정
    private List<TreeDetailTreeItemResponseDto> treeItemList;


    /** 트리 생성, 수정 constructor */
    public TreeDetailResponseDto(Tree tree) {
        this.treeId = tree.getTreeId();
        this.title = tree.getTitle();
        this.linkId = tree.getLinkId();
        this.sendId = tree.getSendId();
        this.ownerType = tree.getOwnerType();
        this.shareType = tree.getShareType();
        this.treeType = tree.getTreeType();
        this.bgType = tree.getBgType();
        this.updatedAt = tree.getUpdatedAt();
    }

    /** 트리 상세정보 constructor */
    public TreeDetailResponseDto(Tree tree,  List<TreeDetailTreeItemResponseDto> treeItemList) {
        this.treeId = tree.getTreeId();
        this.title = tree.getTitle();
        this.linkId = tree.getLinkId();
        this.sendId = tree.getSendId();
        this.ownerType = tree.getOwnerType();
        this.treeType = tree.getTreeType();
        this.bgType = tree.getBgType();
        this.updatedAt = tree.getUpdatedAt();
        this.treeItemList = treeItemList;
    }
}
