package com.bucwith.common.config.oauth;

import com.bucwith.common.config.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.bucwith.common.code.ApiCode.*;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            request.setAttribute("exception", WRONG_TYPE_TOKEN.getCode());
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            request.setAttribute("exception", EXPIRED_TOKEN.getCode());
            filterChain.doFilter(request, response);
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", UNSUPPORTED_TOKEN.getCode());
            log.error("JWT token is unsupported: {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", EMPTY_JWT.getCode());
            log.error("JWT claims string is empty: {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (SignatureException e){
            request.setAttribute("exception", INVALID_JWT.getCode());
            log.error("Invalid JWT signature: {}", e.getMessage());
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("token : {}", jwtService.getJwt());
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            request.setAttribute("exception", UNKNOWN_ERROR.getCode());
            filterChain.doFilter(request, response);
        }
    }
}

