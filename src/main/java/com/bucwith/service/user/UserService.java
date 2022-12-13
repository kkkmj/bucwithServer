package com.bucwith.service.user;

import com.bucwith.domain.user.User;
import com.bucwith.dto.community.CommuResDto;
import com.bucwith.dto.user.UserIconReqDto;
import com.bucwith.dto.user.UserInfoResDto;
import com.bucwith.repository.community.CommunityRepository;
import com.bucwith.repository.user.UserRepository;
import com.bucwith.dto.user.UserNameReqDto;
import com.bucwith.service.community.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service

public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long updateName(Long userId, UserNameReqDto reqDto) {
        User user = getUser(userId);
        user.updateName(reqDto.getName());
        return userId;
    }

    @Transactional
    public Long updateIcon(Long userId, UserIconReqDto reqDto){
        User user = getUser(userId);
        user.updateIcon(reqDto.getIconCode(), reqDto.getBgColor());
        return userId;
    }

    @Transactional(readOnly = true)
    public UserInfoResDto findUserInfo(Long userId){
        return new UserInfoResDto(getUser(userId));
    }


    public User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));
    }
}
