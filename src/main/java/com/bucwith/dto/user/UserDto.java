package com.bucwith.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private int userId;
    private String email;
    private String name;

    @Builder
    public UserDto(int userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}


