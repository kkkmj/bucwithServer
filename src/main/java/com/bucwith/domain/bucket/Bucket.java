package com.bucwith.domain.bucket;

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
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bucketId;
    private String contents;
    private Integer userId;

    @Enumerated(EnumType.STRING)
    private BucketType type;

    @CreationTimestamp
    private LocalDateTime registDate;
}
