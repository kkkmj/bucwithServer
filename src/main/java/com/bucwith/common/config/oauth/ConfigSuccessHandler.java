package com.bucwith.common.config.oauth;

import com.bucwith.common.config.JwtService;
import com.bucwith.common.config.oauth.dto.CustomUserDetail;
import com.bucwith.dto.user.UserResponseDto;
import com.bucwith.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class ConfigSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

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

        String targetUrl;
        log.info("토큰 발행 시작");
        String token = jwtService.createJwt(userId, name);
        //OAuthToken token = jwtService.createJwt(user.getUserId(), user.getName());
        log.info("{}", token);

        //TODO 코드너무 더러움..
        if(user.getIsSign()==Boolean.TRUE){
            /* 로그인 뒤 redirect처리*/
            targetUrl = UriComponentsBuilder.fromUriString("http://61.97.184.195:80/me/list")
                    .queryParam("token", token)
                    .build().toUriString();
            if (response.isCommitted()) {
                logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
                return;
            }
            response.setContentType("application/json;charset=UTF-8");

            response.addHeader("Authorization", token);
            String result = objectMapper.writeValueAsString(new UserResponseDto(user, token));

            response.getWriter().write(result);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
        else {
            /* 로그인 뒤 redirect처리*/
            targetUrl = UriComponentsBuilder.fromUriString("http://61.97.184.195:80/nickname")
                    .queryParam("token", token)
                    .build().toUriString();
            if (response.isCommitted()) {
                logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
                return;
            }
            response.setContentType("application/json;charset=UTF-8");

            response.addHeader("Authorization", token);
            String result = objectMapper.writeValueAsString(new UserResponseDto(user, token));

            response.getWriter().write(result);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }

    }

}
