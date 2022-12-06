package com.bucwith.common.config.oauth.dto;

import com.bucwith.domain.account.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class CustomUserDetail implements UserDetails, OAuth2User {

    private Long userId;
    //private String email;
    private String uname;
    private Boolean isSign;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetail(Long userId, String uname, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        //this.email = email;
        this.uname = uname;
        this.authorities = authorities;
    }
    public CustomUserDetail(Long userId, String uname, Boolean isSign,Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        //this.email = email;
        this.uname = uname;
        this.isSign = isSign;
        this.authorities = authorities;
    }

    public static CustomUserDetail create(User user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetail(
                user.getUserId(),
                //user.getEmail(),
                user.getName(),
                authorities
        );
    }
    public static CustomUserDetail create(User user, Map<String, Object> attributes, Boolean isSign) {
        CustomUserDetail userDetails = CustomUserDetail.create(user);
        userDetails.setAttributes(attributes);
        userDetails.setIsSign(isSign);
        return userDetails;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return uname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return String.valueOf(userId);
    }
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    public void setIsSign(Boolean isSign){
        this.isSign = isSign;
    }
    public Boolean getIsSign(){return isSign;}
}
