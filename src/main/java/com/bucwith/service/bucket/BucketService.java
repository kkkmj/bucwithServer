package com.bucwith.service.bucket;


import com.bucwith.domain.bucket.Bucket;
import com.bucwith.repository.bucket.BucketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BucketService {

    private final BucketRepository bucketRepository;

    public Bucket register(Bucket bucket){
        return bucketRepository.save(bucket);
    }

    public Bucket getBucketById(int bucketId){
        return bucketRepository.findById(bucketId)
                .orElseThrow(() -> new NullPointerException("NOT FOUND BUCKET"));
    }

    public List<Bucket> getBucketByUserId(int userId){
        return bucketRepository.findByUserId(userId);
    }

    public void remove(int bucketId){
        bucketRepository.deleteById(bucketId);
    }
}
