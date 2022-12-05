package com.bucwith.dto.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.community.Comment;
import com.bucwith.domain.community.Community;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter

public class CommentResDto {
    private Long commentId;
    private Long parentId;
    private Long userId;
    private String userName;
    private String content;
    private Boolean secret;
    private LocalDateTime registDate;

    public CommentResDto(Comment entity){
        this.commentId = entity.getCommentId();
        this.parentId = entity.getParentId();
        this.userId = entity.getUser().getUserId();
        this.userName = entity.getUser().getName();
        this.content = entity.getContent();
        this.secret = entity.getSecret();
        this.registDate = entity.getRegistDate();
    }

}
