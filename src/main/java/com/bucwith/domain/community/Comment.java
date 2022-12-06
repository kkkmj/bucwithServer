package com.bucwith.domain.community;

import com.bucwith.domain.account.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commuId")
    private Community community;

    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    private String content;

    private Boolean secret;

    @CreationTimestamp
    private LocalDateTime registDate;

    @Builder
    public Comment(Community community, Long parentId, User user, String content, Boolean secret) {
        this.community = community;
        this.parentId = parentId;
        this.user = user;
        this.secret = secret;
        this.content = content;
    }

    public void modify(String content, Boolean secret){
        this.content = content;
        this.secret = secret;
    }
}
