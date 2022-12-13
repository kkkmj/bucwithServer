package com.bucwith.dto.user;

import com.bucwith.domain.user.Role;
import com.bucwith.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class UserInfoResDto {
    private Long userId;
    private String name;
    private String email;
    private String iconCode; //아이콘 코드
    private String bgColor; //배경컬러
    private LocalDateTime registDate;

    @Builder
    public UserInfoResDto(User entity){
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.iconCode = entity.getIconCode();
        this.bgColor = entity.getBgColor();
        this.registDate = entity.getRegistDate();
    }
}
