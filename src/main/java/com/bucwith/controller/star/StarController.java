package com.bucwith.controller.star;


import com.bucwith.common.CommController;
import com.bucwith.domain.star.Star;
import com.bucwith.dto.star.StarReqDto;
import com.bucwith.service.icon.IconService;
import com.bucwith.service.star.StarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/star")
public class StarController extends CommController {

    private final StarService starService;

    /*
     * Star(응원별) 등록
     * Request Data : StarReqDto
     * Response Data : 등록한 Star(응원별) 반환
     */
    @PostMapping
    public ResponseEntity register(@Validated @RequestBody StarReqDto reqDto) {
        Star star = starService.register(reqDto);

        return SuccessReturn(star);
    }

    /*
     * Star(응원별) 조회
     * Request Data : bucketId
     * iconCode -> Icon 조회 및 반환 필요.
     * Response Data : bucketId로 조회한 List<Star> 반환
     */
    @GetMapping("/{bucketId}")
    public ResponseEntity select(@PathVariable Integer bucketId) {
        List<Star> stars = starService.findStarByBucketId(bucketId);

        return SuccessReturn(stars);
    }

    /*
     * Star(응원별) 삭제
     * Request Data : starId (삭제할 star Id)
     * Response Data : ""
     */
    @DeleteMapping("/{starId}")
    public ResponseEntity delete(@PathVariable Integer starId) {
        starService.remove(starId);

        return SuccessReturn();
    }
}