package com.bucwith.service.bucket;


import com.bucwith.domain.bucket.Bucket;
import com.bucwith.domain.user.User;
import com.bucwith.dto.bucket.BucketModifyReqDto;
import com.bucwith.dto.bucket.BucketResDto;
import com.bucwith.repository.bucket.BucketRepository;
import com.bucwith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BucketService {

    private final UserService userService;
    private final BucketRepository bucketRepository;

    public Bucket register(Bucket bucket) {
        return bucketRepository.save(bucket);
    }

    public Bucket modify(BucketModifyReqDto reqDto) {
        return bucketRepository.save(getBucket(reqDto.getBucketId()).modify(reqDto));
    }

    public void remove(long bucketId) {
        bucketRepository.deleteById(bucketId);
    }

    public Bucket finished(long bucketId) {
        return bucketRepository.save(getBucket(bucketId).finished());
    }

    public Bucket getBucket(long bucketId) {
        return bucketRepository.findById(bucketId)
                .orElseThrow(() -> new NullPointerException("해당 Bucket이 없습니다. bucketId=" + bucketId));
    }

    public BucketResDto getBucketResDto(long bucketId) {
        Bucket bucket = getBucket(bucketId);
        User user =  userService.getUser(bucket.getUserId());
        return new BucketResDto(user, bucket);
    }

    // Buckets 조회 - 달성한 리스트는 하단 > 등록날짜 최신 순 정렬
    public List<Bucket> getBuckets(long userId) {
        return bucketRepository.findByUserId(userId).stream().sorted(
                        Comparator.comparing(Bucket::getIsFinished).reversed()
                                .thenComparing(Bucket::getRegistDate).reversed())
                .collect(Collectors.toList());
    }
}
