package com.bucwith.common.config.oauth;

import com.bucwith.common.code.ApiCode;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bucwith.common.code.ApiCode.*;

@Slf4j
@Getter
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        String exception = String.valueOf(request.getAttribute("exception"));
    log.info("{}",request.getAttribute("exception"));
        if(exception == null) {
            setResponse(response, UNKNOWN_ERROR);
        }
        else if(exception.isEmpty()){
            setResponse(response, UNKNOWN_ERROR);

        }
        //잘못된 타입의 토큰인 경우
        else if(exception.equals(String.valueOf(WRONG_TYPE_TOKEN.getCode()))) {
            setResponse(response, WRONG_TYPE_TOKEN);
        }
        //토큰 만료된 경우
        else if(exception.equals(String.valueOf(EXPIRED_TOKEN.getCode()))) {
            setResponse(response, EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(String.valueOf(UNSUPPORTED_TOKEN.getCode()))) {
            setResponse(response, UNSUPPORTED_TOKEN);
        }
        //변조된 사인 토큰인 경우
        else if(exception.equals(String.valueOf(INVALID_JWT.getCode()))) {
            setResponse(response, INVALID_JWT);
        }
        //jwt가 없는 경우
        else if(exception.equals(String.valueOf(EMPTY_JWT.getCode()))) {
            setResponse(response, EMPTY_JWT);
        }
        else {
            setResponse(response, ACCESS_DENIED);
        }
    }
    private void setResponse(HttpServletResponse response, ApiCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("codeMsg", exceptionCode.getMsg());
        responseJson.put("code", String.valueOf(exceptionCode.getCode()));

        response.getWriter().print(responseJson);
    }
}
