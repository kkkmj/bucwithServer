package com.bucwith.service.icon;


import com.bucwith.domain.icon.Icon;
import com.bucwith.repository.icon.IconRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IconService {

    private final IconRepository iconRepository;

    public Icon findIconByCode(String code) {
        return iconRepository.findByIconCode(code)
                .orElseThrow(() -> new NullPointerException("NOT FOUND ICON"));
    }
}
