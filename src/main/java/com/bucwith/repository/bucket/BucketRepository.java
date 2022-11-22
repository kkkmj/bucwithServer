package com.bucwith.repository.bucket;


import com.bucwith.domain.bucket.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Integer>, JpaSpecificationExecutor<Bucket> {

    Optional<Bucket> findByBucketId(int bucketId);

    List<Bucket> findByUserId(int userId);
}

