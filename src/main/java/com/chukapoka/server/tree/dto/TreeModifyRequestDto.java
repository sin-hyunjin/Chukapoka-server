package com.chukapoka.server.tree.dto;

import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.BgType;
import com.chukapoka.server.common.enums.OwnerType;
import com.chukapoka.server.common.enums.ShareType;
import com.chukapoka.server.common.enums.TreeType;
import com.chukapoka.server.tree.entity.Tree;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TreeModifyRequestDto {

    /** 트리에 관련된 것만 수정할것 */
    @NotBlank(message = "title is null")
    private String title;

    @NotBlank(message = "ownerType is null")
    @ValidEnum(enumClass = OwnerType.class, message = "OwnerType must be MINE or NOT_YET_SEND")
    private String ownerType;

    @NotBlank(message = "shareType is null")
    @ValidEnum(enumClass = ShareType.class, message = "shareType must be ONLY or TOGETHER")
    private String shareType;

    @NotBlank(message = "treeType is null")
    @ValidEnum(enumClass = TreeType.class, message = "TreeType must be TREE_TYPE_*")
    private String treeType;

    @NotBlank(message = "bgType is null")
    @ValidEnum(enumClass = BgType.class, message = "bgType must be BG_TYPE_")
    private String bgType;


    public Tree toEntity(Tree tree) {
        return Tree.builder()
                .treeId(tree.getTreeId())
                .linkId(tree.getLinkId())
                .sendId(tree.getSendId())
                .updatedBy(tree.getUpdatedBy())

                .title(this.title)
                .ownerType(this.ownerType)
                .shareType(this.shareType)
                .treeType(this.treeType)
                .bgType(this.bgType)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
