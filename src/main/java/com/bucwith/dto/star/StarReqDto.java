package com.bucwith.dto.star;

import com.bucwith.domain.icon.Icon;
import com.bucwith.domain.star.Star;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StarReqDto {
    @NotNull private Integer bucketId;
    @NotBlank private String nickname;
    @NotBlank private String contents;
    @NotBlank private String iconCode; // icon Table 조회를 위한 Code

    public Star toEntity(Icon icon) {
        return Star.builder()
                .bucketId(bucketId)
                .nickname(nickname)
                .contents(contents)
                .icon(icon)
                .build();
    }
}
