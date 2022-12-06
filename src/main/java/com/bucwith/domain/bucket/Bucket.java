package com.bucwith.domain.bucket;

import com.bucwith.dto.bucket.BucketModifyReqDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bucketId;
    private Integer userId;
    private String contents;
    private Boolean isFinished;

    @Enumerated(EnumType.STRING)
    private BucketType type;

    @CreationTimestamp
    private LocalDateTime registDate;

    public Bucket modify(BucketModifyReqDto reqDto) {
        this.contents = reqDto.getContents();
        this.type = reqDto.getType();
        return this;
    }

    public Bucket finished() {
        this.isFinished = !isFinished;
        return this;
    }
}
