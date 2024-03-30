package com.chukapoka.server.tree.dto;

import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.BgType;
import com.chukapoka.server.common.enums.OwnerType;
import com.chukapoka.server.common.enums.ShareType;
import com.chukapoka.server.common.enums.TreeType;
import com.chukapoka.server.tree.entity.Tree;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.UUID;

@Data
public class TreeCreateRequestDto {
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



    /** Create Tree Build*/
    public Tree toEntity(TreeCreateRequestDto treeRequestDto, long userId) {
        UUID linkId = UUID.randomUUID();
        UUID sendId = UUID.randomUUID();

        LocalDateTime updatedAt = LocalDateTime.now();
        String encodedLinkId = Base64.getEncoder().encodeToString(encoder().encode(linkId + "-link-" + userId + updatedAt  + updatedAt.atZone(ZoneId.systemDefault()).toEpochSecond()).getBytes());
        String encodedSendId = "";
        if (treeRequestDto.ownerType.equals(OwnerType.NOT_YET_SEND.getValue())) {
            encodedSendId = Base64.getEncoder().encodeToString(encoder().encode(sendId + "-send-"+ userId + updatedAt + updatedAt.atZone(ZoneId.systemDefault()).toEpochSecond()).getBytes());
        }
        return Tree.builder()
                .title(treeRequestDto.title)
                .ownerType(treeRequestDto.ownerType)
                .shareType(treeRequestDto.shareType)
                .linkId(encodedLinkId)
                .sendId(encodedSendId)
                .treeType(treeRequestDto.treeType)
                .bgType(treeRequestDto.bgType)
                .updatedBy(userId)
                .updatedAt(updatedAt)
                .build();
    }

    // linkId, sendId 암호화를 위해  BCryptPasswordEncoder 등록
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}