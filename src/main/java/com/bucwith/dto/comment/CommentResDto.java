package com.bucwith.dto.comment;

import com.bucwith.domain.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

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
    public CommentResDto SecretContent(){
        this.content = "비밀댓글입니다.";
        return this;
    }

}
