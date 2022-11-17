package com.bucwith.common.config.oauth;

import com.bucwith.common.config.JwtService;
import com.bucwith.common.config.oauth.dto.OAuthAttributes;
import com.bucwith.common.config.oauth.dto.OAuthToken;
import com.bucwith.dto.user.UserDto;
import com.bucwith.mapper.user.UserRequestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.bucwith.domain.account.Role.USER;


@Slf4j
@RequiredArgsConstructor
@Component
public class ConfigSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRequestMapper userRequestMapper;

    /**
     * success handler
     * oauth 로그인 성공했을시 토큰 발행
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
        // 최초 로그인이라면 회원가입 처리를 한다.
        String targetUrl;
        log.info("토큰 발행 시작");
        OAuthToken token = jwtService.createJwt(userDto.getEmail(), USER);
        log.info("{}", token);
        /* 로그인 뒤 redirect처리*/
        /*targetUrl = UriComponentsBuilder.fromUriString("/user/name")
                .queryParam("token", "token")
                .build().toUriString();
        //getRedirectStrategy().sendRedirect(request, response, targetUrl);*/
    }


}
