package com.bucwith.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNameReqDto {
    private String name;

    @Builder
    public UserNameReqDto(String name){
        this.name = name;
    }
}
