package com.bucwith.dto.bucket;

import com.bucwith.domain.bucket.BucketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BucketModifyReqDto {
    @NotNull private Integer bucketId;
    @NotBlank private String contents;
    @NotNull private BucketType type;
}
