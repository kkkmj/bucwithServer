package com.bucwith.repository.star;


import com.bucwith.domain.star.Star;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface StarRepository extends JpaRepository<Star, Integer>, JpaSpecificationExecutor<Star> {
    Slice<Star> findByBucketId(int bucketId, Pageable pageable);
}

