package com.bucwith.service.user;

import com.bucwith.domain.user.User;
import com.bucwith.dto.user.UserIconReqDto;
import com.bucwith.repository.user.UserRepository;
import com.bucwith.dto.user.UserNameReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Long updateIcon(Long userId, UserIconReqDto reqDto){
        User user = getUser(userId);
        user.updateIcon(reqDto.getIconCode(), reqDto.getBgColor());
        return userId;
    }

    public User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));
    }
}
