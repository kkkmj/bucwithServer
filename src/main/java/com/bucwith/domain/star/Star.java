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
    @Enumerated(EnumType.STRING)
    private IconCode iconCode;

    @CreationTimestamp
    private LocalDateTime registDate;
}
