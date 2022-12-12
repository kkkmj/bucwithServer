package com.bucwith.domain.star;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer starId;
    private Integer bucketId;
    private String contents;
    private String nickname;
    private String iconCode;

//    @OneToOne
//    @JoinColumn(name="iconCode")
//    private Icon icon;

    @CreationTimestamp
    private LocalDateTime registDate;
}
