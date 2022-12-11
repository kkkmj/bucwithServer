package com.bucwith.common.config;

import com.bucwith.common.config.oauth.dto.CustomUserDetail;
import com.bucwith.common.config.oauth.secret.Secret;
import com.bucwith.common.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

import static com.bucwith.common.code.ApiCode.EMPTY_JWT;
import static com.bucwith.common.code.ApiCode.INVALID_JWT;
import static com.bucwith.common.config.oauth.secret.Secret.JWT_SECRET_KEY;


/**
 * JWT 토큰 서비스
 *
 * jwt토큰 생성, 얻기, 인증, 검증, 토큰에서 userId 추출
 *
 */
@Service
public class JwtService {

    private static final String AUTHORITIES_KEY = "role";

    /**
        JWT 생성
        @param userId, role
        @return String
         **/
    public String createJwt(Long userId, String name){
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId",userId)
                .claim("name", name)
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant()))
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*10)))  //둘다 date를 파라미터로 받기 때문에 그냥 안바꿈
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    /**
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     **/
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }


    /**
     * 토큰 인증
     * User는 SpringSecurity UserDetails의 User
     *
     * 유저 인증토큰에 필요한 것들(Security유저객체, 토큰, 인증) 을 리턴
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token){
        // 디비를 거치지 않고 토큰에서 값을 꺼내 바로 시큐리티 유저 객체를 만들어 Authentication을 만들어 반환하기에 유저네임, 권한 외 정보는 알 수 없다.

        //CustomUserDetail userDetail = userDetailService.loadUserById(this.getUserId(token));

        CustomUserDetail userDetail = new CustomUserDetail(getUserId(token),getUserName(token),Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return new UsernamePasswordAuthenticationToken(userDetail, token, userDetail.getAuthorities());
    }


    /**
     * jwt 검증
     *
     * jwt의 기한이 다 되었는지 검증
     * @param jwtToken
     * @return
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
    JWT에서 userId 추출
    @return int
    @throws BaseException
     */
    public Long getUserId() throws BaseException{
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("userId", Long.class);  // jwt 에서 userId를 추출합니다.
    }
    public Long getUserId(String token){
        return Jwts.parser().setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(token).getBody().get("userId", Long.class);
    }
    public String getUserName(String token){
        return Jwts.parser().setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(token).getBody().get("name", String.class);
    }


}

