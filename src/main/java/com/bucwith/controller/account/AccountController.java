package com.bucwith.controller.account;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.bucwith.common.config.oauth.secret.Secret.AUTHORIZATION;
import static com.bucwith.common.config.oauth.secret.Secret.BEARER;

@RequestMapping("/test")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController extends CommController {
    private final JwtService jwtService;

    @GetMapping("/token/{userId}")
    public ResponseEntity exampleToken(@PathVariable("userId") String userId) {
        Long id = Long.valueOf(userId);
        String Token = jwtService.createJwt(id);
        //String BearerToken = BEARER + " " + Token;
        //Map<String, String> token = new HashMap<>();
        //token.put(AUTHORIZATION, BearerToken);
        log.info("{}",Token);
        return SuccessReturn(Token);
    }
}
