package com.bucwith.service.user;

import com.bucwith.domain.account.User;
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
    public Long update(Long id, UserNameReqDto reqDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + id));

        user.update(reqDto.getName());

        return id;
    }
}
