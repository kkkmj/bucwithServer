package com.bucwith.dto.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.community.Comment;
import com.bucwith.domain.community.Community;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentSaveReqDto {
    private Community community;
    private int replyId;
    private User user;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private Boolean secret;

    @Builder
    public CommentSaveReqDto(Community community, int replyId, User user, String content, Boolean secret){
        this.community = community;
        this.replyId = replyId;
        this.user = user;
        this.content = content;
        this.secret = secret;
    }

    public Comment toEntity(){
        return Comment.builder()
                .community(community)
                .replyId(replyId)
                .user(user)
                .content(content)
                .secret(secret)
                .build();
    }
}
