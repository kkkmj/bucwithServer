package com.bucwith.common.config;

import com.bucwith.common.config.oauth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final ConfigSuccessHandler successHandler;


    /** Spring Security
     *
     * Security 설정
     * /login/**에서 모든 권한 허용
     * 나머지는 인가된 사용자에게만
     *
     * jwt 토큰을 받아 필터 수행
     *
     * OAuth2 로그인 성공시 CustomOAuth2Service로 이동

     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                //csrf 공격을 막아주는 옵션을 disalbe, rest api같은 경우에는 브라우저를 통해 request 받지 않기 때문에 해당 옵션을 꺼도 됨
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 스프링 시큐리티가 세션 쿠키 방식으로 동작하지 않도록 설정
                .and()

                // authorizeRequests
                // URL별 권한 관리를 설정하는 옵션의 시작점이다. authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
                .authorizeRequests()

                // antMatchers
                // 권한 관리 대상을 지정하는 옵션이다.
                // URL, HTTP 메소드별로 관리가 가능하다. 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 관한을 준다.
                .antMatchers("/","/test/**", "/login/**").permitAll()
                .antMatchers("/oauth2/**", "/auth/**").permitAll()
                .antMatchers("/bucket/**").authenticated()

                // anyRequest
                // 설정된 값 이외의 나머지 URL들을 나타낸다. authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자들(로그인한 사용자들)에게만 허용한다.
                .anyRequest().permitAll()
                .and()

                // 로그아웃 기능에 대한 여러 설정의 진입점이다. 로그아웃 성공시 "/" 주소로 이동한다.
                .logout()
                .logoutSuccessUrl("/")
                .and()

                // oauth2Login
                // OAuth 2 로그인 기능에 대한 여러 설정의 진입점이다.
                .oauth2Login()
                /*.loginPage("/oauth2Login")
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*") // 디폴트는 login/oauth2/code/*
                .and()*/
                //.defaultSuccessUrl("/oauth2/loginInfo", true) //OAuth2 성공시 redirect

                // userInfoEndpoint
                // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당한다.
                .userInfoEndpoint()

                // userService
                // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.
                // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시할 수 있다.
                .userService(customOAuth2UserService)
                .and()
                //성공시 토큰반환해주는 handler
                .successHandler(successHandler);
                //.failureHandler(configFailureHandler())

        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

    }



}