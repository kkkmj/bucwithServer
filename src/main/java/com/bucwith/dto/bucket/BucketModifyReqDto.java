package com.bucwith.dto.bucket;

import com.bucwith.domain.bucket.BucketType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BucketModifyReqDto {
    @NotNull private Integer bucketId;
    @NotBlank private String contents;
    @NotNull private BucketType type;
}
