package com.bucwith.repository.star;


import com.bucwith.domain.star.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StarRepository extends JpaRepository<Star, Integer>, JpaSpecificationExecutor<Star> {

    Optional<Star> findByStarId(int bucketId);

    List<Star> findByBucketId(int bucketId);
}

