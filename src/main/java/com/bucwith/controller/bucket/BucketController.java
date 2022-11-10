package com.bucwith.controller.bucket;


import com.bucwith.common.CommController;
import com.bucwith.domain.bucket.Bucket;
import com.bucwith.dto.bucket.BucketReqDto;
import com.bucwith.service.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bucket")
public class BucketController extends CommController {

    private final BucketService bucketService;

    /*
     * Bucket 등록
     * Request Data : BucketDto
     * Response Data : 등록한 Bucket 반환
     */
    @PostMapping("/register")
    public ResponseEntity register(@Validated @RequestBody BucketReqDto reqDto) {

        Bucket bucket = bucketService.register(Bucket.builder()
                .userId(reqDto.getUserId())
                .contents(reqDto.getContents())
                .build()
        );

        return SuccessReturn(bucket);
    }
}