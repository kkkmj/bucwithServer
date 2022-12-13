package com.bucwith.controller.user;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.exception.BaseException;
import com.bucwith.dto.user.UserIconReqDto;
import com.bucwith.dto.user.UserNameReqDto;
import com.bucwith.service.community.CommunityService;
import com.bucwith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController extends CommController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CommunityService communityService;

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
        return SuccessReturn(userService.updateName(userId, reqDto));

    }

    /**
     * 유저 icon 수정
     * @param reqDto (iconCode, bgColor)
     * @return
     * @throws BaseException
     */
    @PutMapping("/icon")
    public ResponseEntity updateIcon(@Validated @RequestBody UserIconReqDto reqDto) throws BaseException{
        Long userId = jwtService.getUserId();
        return SuccessReturn(userService.updateIcon(userId, reqDto));
    }

    @GetMapping("/info")
    public ResponseEntity findUserInfo() throws BaseException{
        Long userId = jwtService.getUserId();
        return SuccessReturn(userService.findUserInfo(userId));
    }

}


