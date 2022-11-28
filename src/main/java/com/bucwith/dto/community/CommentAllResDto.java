package com.bucwith.dto.community;

import com.bucwith.domain.community.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentAllResDto {
    private Long commentId;
    private Long commuId;
    private Long replyId;
    private Long userId;
    private String userName;
    private String content;
    private Boolean secret;
    private LocalDateTime registDate;
    private List<CommentResDto> replys;

    public CommentAllResDto(Comment entity, List<CommentResDto> replys){
        this.commentId = entity.getComId();
        this.commuId = entity.getCommunity().getCommuId();
        this.replyId = entity.getReplyId();
        this.userId = entity.getUser().getUserId();
        this.userName = entity.getUser().getName();
        this.content = entity.getContent();
        this.secret = entity.getSecret();
        this.registDate = entity.getRegistDate();
        this.replys = replys;
    }
}
