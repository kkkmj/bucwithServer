package com.bucwith.repository.star;


import com.bucwith.domain.star.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StarRepository extends JpaRepository<Star, Integer>, JpaSpecificationExecutor<Star> {
    List<Star> findByBucketId(int bucketId);
}

