package com.bucwith.service.account;

import com.bucwith.common.code.ApiCode;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.config.oauth.dto.CustomUserDetail;
import com.bucwith.common.config.oauth.dto.OAuthToken;
import com.bucwith.common.exception.BaseException;
import com.bucwith.domain.user.User;
import com.bucwith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final JwtService jwtService;
    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public OAuthToken reissueToken(OAuthToken oAuthToken) throws BaseException {
        if(!jwtService.validateToken(oAuthToken.getRefreshToken())){
            throw new BaseException(ApiCode.INVALID_JWT);
        }
        String accessToken = oAuthToken.getAccessToken();
        Authentication authentication = jwtService.getAuthentication(accessToken);
        CustomUserDetail authenticationUser = (CustomUserDetail) authentication.getPrincipal();
        User user = userService.getUser(jwtService.getUserId(accessToken));
        String refreshToken = redisTemplate.opsForValue().get("RefreshToken:" +authenticationUser.getUserId());
        if(!refreshToken.equals(oAuthToken.getRefreshToken())){
            throw new BaseException(ApiCode.INVALID_JWT);
        }

        OAuthToken newToken = jwtService.createJwt(user.getUserId(), user.getName());

        redisTemplate.opsForValue().set("RefreshToken:" + authenticationUser.getUserId(), newToken.getRefreshToken(),jwtService.getExpiration(newToken.getRefreshToken()), TimeUnit.MILLISECONDS);
        return newToken;
    }
}
