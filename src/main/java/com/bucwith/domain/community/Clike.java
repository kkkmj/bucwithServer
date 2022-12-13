package com.bucwith.domain.community;

import com.bucwith.domain.user.User;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Clike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commuId")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @Builder
    public Clike(Community community, User user) {
        this.community = community;
        this.user = user;
    }
}
