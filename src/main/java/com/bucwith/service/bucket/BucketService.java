package com.bucwith.service.bucket;


import com.bucwith.domain.bucket.Bucket;
import com.bucwith.repository.bucket.BucketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class BucketService {

    private final BucketRepository bucketRepository;

    public Bucket register(Bucket bucket){
        return bucketRepository.save(bucket);
    }
}
