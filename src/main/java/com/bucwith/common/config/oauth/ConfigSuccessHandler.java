package com.bucwith.common.config.oauth;

import com.bucwith.common.config.JwtService;
import com.bucwith.common.config.oauth.dto.CustomUserDetail;
import com.bucwith.common.config.oauth.dto.OAuthToken;
import com.bucwith.dto.user.UserResponseDto;
import com.bucwith.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Component
public class ConfigSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * success handler
     * oauth 로그인 성공했을시 토큰 발행
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {


        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        Long userId = Long.valueOf(user.getName());
        String name = user.getUname();


        log.info("토큰 발행 시작");
        OAuthToken token = jwtService.createJwt(userId, name);
        redisTemplate.opsForValue().set("RefreshToken:" + userId, token.getRefreshToken(),jwtService.getExpiration(token.getRefreshToken()), TimeUnit.MILLISECONDS);


        log.info("{}", token);
        String targetUrl = getUrl(token, user);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    public String getUrl(OAuthToken token, CustomUserDetail user){
        String path = Objects.equals(user.getUname(), "N") ? "nickname" : "me/list";
        return UriComponentsBuilder.fromUriString("http://localhost:3000/{path}")
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .buildAndExpand(path).toUriString();
    }

}