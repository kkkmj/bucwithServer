package com.bucwith.common.config.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Getter
public class OAuthToken {
    private String accessToken;
    private String refreshToken;

    public OAuthToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

