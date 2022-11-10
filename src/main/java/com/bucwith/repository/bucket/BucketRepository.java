package com.bucwith.repository.bucket;


import com.bucwith.domain.bucket.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BucketRepository extends JpaRepository<Bucket, Integer>, JpaSpecificationExecutor<Bucket> {
}

