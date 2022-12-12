package com.bucwith.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String iconCode; //아이콘 코드
    private String bgColor; //배경컬러

    @CreationTimestamp
    private LocalDateTime registDate;


    @Builder
    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }
    public void updateIcon(String iconCode, String bgColor){
        this.iconCode = iconCode;
        this.bgColor = bgColor;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }


}
