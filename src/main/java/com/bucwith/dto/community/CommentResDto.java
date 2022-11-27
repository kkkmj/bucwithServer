package com.bucwith.dto.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.community.Comment;
import com.bucwith.domain.community.Community;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter

public class CommentResDto {
    private Long comId;
    private Long commuId;
    private int replyId;
    private String userName;
    private String content;
    private Boolean secret;
    private LocalDateTime registDate;


    public CommentResDto(Comment entity){
        this.comId = entity.getComId();
        this.commuId = entity.getCommunity().getCommuId();
        this.replyId = entity.getReplyId();
        this.userName = entity.getUser().getName();
        this.content = entity.getContent();
        this.secret = entity.getSecret();
        this.registDate = entity.getRegistDate();
    }

}
