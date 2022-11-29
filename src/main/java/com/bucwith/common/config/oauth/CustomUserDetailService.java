package com.bucwith.common.config.oauth;

import com.bucwith.common.config.oauth.dto.CustomUserDetail;
import com.bucwith.domain.account.User;
import com.bucwith.domain.community.CommuCate;
import com.bucwith.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUserDetail loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));
        CustomUserDetail customUser = CustomUserDetail.create(user);

        return customUser;
    }
}
