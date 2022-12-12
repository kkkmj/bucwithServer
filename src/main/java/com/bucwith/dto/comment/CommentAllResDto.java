package com.bucwith.dto.comment;

import com.bucwith.domain.comment.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentAllResDto {
    private Long commentId;
    private Long commuId;
    private Long parentId;
    private Long userId;
    private String userName;
    private String content;
    private Boolean secret;
    private LocalDateTime registDate;
    private List<CommentResDto> replys;

    public CommentAllResDto(Comment entity, List<CommentResDto> replys){
        this.commentId = entity.getCommentId();
        this.commuId = entity.getCommunity().getCommuId();
        this.parentId = entity.getParentId();
        this.userId = entity.getUser().getUserId();
        this.userName = entity.getUser().getName();
        this.content = entity.getContent();
        this.secret = entity.getSecret();
        this.registDate = entity.getRegistDate();
        this.replys = replys;
    }

    public CommentAllResDto SecretContent(){
        this.content = "비밀댓글입니다.";
        return this;
    }

}
