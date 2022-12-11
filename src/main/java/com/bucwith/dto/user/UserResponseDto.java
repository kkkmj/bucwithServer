package com.bucwith.dto.user;

import com.bucwith.common.config.oauth.dto.CustomUserDetail;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    //private String email;
    private String uname;
    private Boolean isSign;
    private String token;

    public UserResponseDto(CustomUserDetail userDetail, String token){
        this.userId = userDetail.getUserId();
        //this.email = userDetail.getEmail();
        this.uname = userDetail.getUname();
        this.isSign = userDetail.getIsSign();
        this.token = token;
    }
}
