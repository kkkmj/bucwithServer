package com.bucwith.domain.community;

import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class CommuCate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ccId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commuId")
    private Community community;

    @Enumerated(EnumType.STRING)
    private Category category;


    @Builder
    public CommuCate(Community community, Category category) {
        this.community = community;
        this.category = category;
    }
}
