package com.bucwith.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentModifyReqDto {
    private String content;
    private Boolean secret;

    @Builder
    public CommentModifyReqDto(String content, Boolean secret){
        this.content = content;
        this.secret = secret;
    }
}
