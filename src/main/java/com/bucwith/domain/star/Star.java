package com.bucwith.domain.star;


import com.bucwith.domain.icon.Icon;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
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

    @OneToOne
    @JoinColumn(name="iconCode")
    private Icon icon;

    @CreationTimestamp
    private LocalDateTime registDate;
}
