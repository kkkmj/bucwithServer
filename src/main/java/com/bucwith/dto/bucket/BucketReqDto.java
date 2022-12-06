package com.bucwith.dto.bucket;

import com.bucwith.domain.bucket.Bucket;
import com.bucwith.domain.bucket.BucketType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BucketReqDto {
    @NotNull private Integer userId;
    @NotBlank private String contents;
    @NotNull private BucketType type;

    public Bucket toEntity() {
        return Bucket.builder()
                .userId(userId)
                .contents(contents)
                .type(type)
                .isFinished(false)
                .build();
    }
}
