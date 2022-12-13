package com.bucwith.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIconReqDto {
    private String iconCode;
    private String bgColor;

    @Builder
    public UserIconReqDto(String iconCode, String bgColor){
        this.iconCode = iconCode;
        this.bgColor = bgColor;
    }
}
