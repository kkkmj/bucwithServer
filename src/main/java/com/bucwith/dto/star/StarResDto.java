package com.bucwith.dto.star;

import com.bucwith.domain.star.Star;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class StarResDto {
    private Integer totalCnt; // star의 총 개수
    private Slice<Star> stars; // 조건에 맞는 star의 집합
}