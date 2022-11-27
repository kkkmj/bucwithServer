package com.bucwith.domain.community;

import com.bucwith.domain.account.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;
    private String content;

    @Enumerated(EnumType.STRING)
    private CommuType type;

    @CreationTimestamp
    private LocalDateTime registDate;

    private int party;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @OrderBy("comId asc")
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @OrderBy("likeId asc")
    private List<Clike> likes = new ArrayList<Clike>();


    @Builder
    public Community(User user, String content, CommuType type, int party) {
        this.user = user;
        this.content = content;
        this.type = type;
        this.party = party;
    }

    }



