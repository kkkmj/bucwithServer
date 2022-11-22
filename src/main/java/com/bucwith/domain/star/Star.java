package com.bucwith.domain.star;

import com.bucwith.domain.icon.Icon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
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

    @CreationTimestamp
    private LocalDateTime registDate;

    @Transient
    private Icon icon;
}
