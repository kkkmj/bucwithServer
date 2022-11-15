package com.bucwith.service.user;

import com.bucwith.domain.account.User;
import com.bucwith.domain.account.UserRepository;
import com.bucwith.dto.user.UserNameReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service

public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String update(String email, UserNameReqDto reqDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + email));

        user.update(reqDto.getName());

        return email;
    }
}
