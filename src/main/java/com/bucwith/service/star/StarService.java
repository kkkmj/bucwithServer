package com.bucwith.service.star;


import com.bucwith.domain.star.Star;
import com.bucwith.repository.star.StarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StarService {

    private final StarRepository starRepository;

    public Star register(Star star){
        return starRepository.save(star);
    }

    public Star getStarByStarId(int starId){
        return starRepository.findByStarId(starId)
                .orElseThrow(() -> new NullPointerException("NOT FOUND BUCKET"));
    }

    public List<Star> getStarByBucketId(int bucketId){
        return starRepository.findByBucketId(bucketId);
    }

    public void remove(int starId){
        starRepository.deleteById(starId);
    }
}
