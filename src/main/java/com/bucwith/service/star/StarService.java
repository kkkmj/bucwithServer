package com.bucwith.service.star;


import com.bucwith.domain.icon.Icon;
import com.bucwith.domain.star.Star;
import com.bucwith.dto.star.StarReqDto;
import com.bucwith.repository.icon.IconRepository;
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
    private final IconRepository iconRepository;

    public Star register(StarReqDto reqDto) {
        Icon icon = iconRepository.findByIconCode(reqDto.getIconCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 Icon이 없습니다. code=" + reqDto.getIconCode()));

        return starRepository.save(reqDto.toEntity(icon));
    }

    public Star findStarById(int starId){
        return starRepository.findById(starId)
                .orElseThrow(() -> new NullPointerException("해당 Star가 없습니다. starId=" + starId));
    }

    public List<Star> findStarByBucketId(int bucketId){
        return starRepository.findByBucketId(bucketId);
    }

    public void remove(int starId){
        starRepository.deleteById(starId);
    }
}
