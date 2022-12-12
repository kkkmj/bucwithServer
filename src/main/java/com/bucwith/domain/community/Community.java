package com.bucwith.domain.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.commuCategory.CommuCate;
import com.bucwith.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
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

    private int viewCnt;

    @CreationTimestamp
    private LocalDateTime registDate;

    private int party;

    private int likeCnt;
    private int commentCnt;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @OrderBy("commentId asc")
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @OrderBy("likeId asc")
    private List<Clike> likes = new ArrayList<Clike>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @OrderBy("ccId asc")
    private List<CommuCate> commuCates = new ArrayList<CommuCate>();



    @Builder
    public Community(User user, String content, CommuType type, int party) {
        this.user = user;
        this.content = content;
        this.type = type;
        this.party = party;
    }

    public void modify(String content, CommuType type, int party){
        this.content = content;
        this.type = type;
        this.party = party;
    }

    }



