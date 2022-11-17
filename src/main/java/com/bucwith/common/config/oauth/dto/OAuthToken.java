package com.bucwith.common.config.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Getter
public class OAuthToken {
    private String token;
    private String refreshToken;

    public OAuthToken(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

