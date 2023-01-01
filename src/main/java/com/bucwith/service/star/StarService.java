package com.bucwith.service.star;


import com.bucwith.domain.star.Star;
import com.bucwith.dto.star.StarReqDto;
import com.bucwith.repository.star.StarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StarService {

    private final StarRepository starRepository;

    public Star register(StarReqDto reqDto) {
        return starRepository.save(reqDto.toEntity());
    }

    public void remove(int starId) {
        starRepository.deleteById(starId);
    }

    public Star getStar(int starId) {
        return starRepository.findById(starId)
                .orElseThrow(() -> new NullPointerException("해당 Star가 없습니다. starId=" + starId));
    }

    public Slice<Star> getStarByBucketId(int bucketId, int currentPage, int starCnt, boolean isDesc) {
        return starRepository.findByBucketId(bucketId, PageRequest.of(currentPage, starCnt,
                        (isDesc) ? Sort.by("registDate").descending() : Sort.by("registDate")));
    }
}
