package com.bucwith.controller.bucket;


import com.bucwith.common.CommController;
import com.bucwith.domain.bucket.Bucket;
import com.bucwith.dto.bucket.BucketModifyReqDto;
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
     * Request Data : BucketReqDto(userId, contents, type)
     * Response Data : 등록한 Bucket 반환
     */
    @PostMapping
    public ResponseEntity register(@Validated @RequestBody BucketReqDto reqDto) {

        Bucket bucket = bucketService.register(Bucket.builder()
                .userId(reqDto.getUserId())
                .contents(reqDto.getContents())
                .type(reqDto.getType())
                .isFinished(false)
                .build()
        );

        return SuccessReturn(bucket);
    }

    /*
     * Bucket 조회
     * Request Data : userId
     * Response Data : userId로 조회한 Bucket 반환
     */
    @GetMapping("/{userId}")
    public ResponseEntity select(@PathVariable Integer userId) {
        return SuccessReturn(bucketService.getBucketByUserId(userId));
    }

    /*
     * Bucket 수정
     * Request Data : BucketReqDto(bucketId, contents, type)
     * Response Data : 수정된 Bucket 반환
     */
    @PutMapping
    public ResponseEntity modify(@Validated @RequestBody BucketModifyReqDto reqDto) {
        Bucket bucket = bucketService.getBucketById(reqDto.getBucketId());
        bucket.setContents(reqDto.getContents());
        bucket.setType(reqDto.getType());

        return SuccessReturn(bucketService.register(bucket));
    }

    /*
     * Bucket 삭제
     * Request Data : bucketId (삭제할 bucket Id)
     * Response Data : ""
     */
    @DeleteMapping("/{bucketId}")
    public ResponseEntity delete(@PathVariable Integer bucketId) {
        bucketService.remove(bucketId);
        return SuccessReturn();
    }

    /*
     * Bucket 완료 True Or False 등록
     * Request Data : bucketId
     * Response Data : 수정된 Bucket 반환
     */
    @PostMapping("/finish/{bucketId}")
    public ResponseEntity setFinished(@PathVariable Integer bucketId) {

        Bucket bucket = bucketService.getBucketById(bucketId);
        bucket.setIsFinished(!bucket.getIsFinished());

        return SuccessReturn(bucketService.register(bucket));
    }
}