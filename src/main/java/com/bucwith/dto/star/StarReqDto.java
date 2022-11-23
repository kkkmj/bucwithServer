package com.bucwith.dto.star;

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
public class StarReqDto {
    @NotNull private Integer bucketId;
    @NotBlank private String nickname;
    @NotBlank private String contents;
    @NotBlank private String iconCode; // icon Table 조회를 위한 Code
}
