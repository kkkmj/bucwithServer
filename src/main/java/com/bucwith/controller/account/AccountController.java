package com.bucwith.controller.account;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.exception.BaseException;
import com.bucwith.common.exception.ExceptionController;
import com.bucwith.dto.user.UserNameReqDto;
import com.bucwith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AccountController extends CommController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 이름 수정
     *  Request Data : UserNameReqDto(user name)
     *  Response Data : success 반환
     * @param reqDto
     * @return
     * @throws BaseException
     */
    @PutMapping("/name")
    public ResponseEntity updateName(@Validated @RequestBody UserNameReqDto reqDto ) throws BaseException {
        Long userId = jwtService.getUserId();
        userService.update(userId, reqDto);
        return SuccessReturn(reqDto);

    }

}


