package com.bucwith.common.config.oauth;

import com.bucwith.common.config.JwtService;
import com.bucwith.common.config.oauth.dto.OAuthAttributes;
import com.bucwith.domain.account.User;
import com.bucwith.domain.account.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * OAuth Login을 통해 들어온 값들을 처리
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final HttpSession httpSession;


    /**
     * OAuth2UserRequest로 들어온 userRequest값들을 db에 저장
     *
     * @param userRequest the user request
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 유저 정보를 담고있음

        // registrationId
        // 햔재 로그인 진행 중인 서비스를 구분하는 코드
        // 네이버 로그인인지, 구글 로그인인지 구분하기위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // userNameAttributeName
        // OAuth2 로그인 진행 시 키가 되는 필드값을 말한다. PK와 같은 의미이다.
        // 구글의 경우 기본적으로 코드를 지원한다. 구글의 기본 코드는 "sub"이다. (네이버, 카카오 등은 기본 지원하지 않음)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes
        // OAuth2UserService를 통해 가져온 OAuth2User의 attributes를 담을 클래스이다.
        // 다른 소셜 로그인도 이 클래스를 사용한다.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        log.info("{}", attributes);

        User user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }



    /**
     * loadUser함수에서 user객체 저장하는 함수
     *
     * @param attributes
     * @return
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
