package com.bucwith.service.bucket;


import com.bucwith.domain.bucket.Bucket;
import com.bucwith.dto.bucket.BucketModifyReqDto;
import com.bucwith.dto.bucket.BucketReqDto;
import com.bucwith.repository.bucket.BucketRepository;
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

    private final BucketRepository bucketRepository;

    public Bucket register(BucketReqDto reqDto) {
        return bucketRepository.save(reqDto.toEntity());
    }

    public Bucket modify(BucketModifyReqDto reqDto) {
        Bucket bucket = findBucketById(reqDto.getBucketId());
        bucket.setContents(reqDto.getContents());
        bucket.setType(reqDto.getType());

        return bucketRepository.save(bucket);
    }

    public void remove(int bucketId) {
        bucketRepository.deleteById(bucketId);
    }

    public Bucket findBucketById(int bucketId) {
        return bucketRepository.findById(bucketId)
                .orElseThrow(() -> new NullPointerException("해당 Bucket이 없습니다. bucketId=" + bucketId));
    }

    public List<Bucket> findBucketByUserId(int userId) {
        List<Bucket> buckets = bucketRepository.findByUserId(userId);
        buckets = buckets.stream().sorted(Comparator.comparing(Bucket::getIsFinished).reversed()
                        .thenComparing(Bucket::getRegistDate).reversed())
                .collect(Collectors.toList());

        return buckets;
    }

    public Bucket setFinished(int bucketId) {
        Bucket bucket = findBucketById(bucketId);
        bucket.setIsFinished(!bucket.getIsFinished());

        return bucketRepository.save(bucket);
    }
}
