package com.bucwith.repository.icon;


import com.bucwith.domain.icon.Icon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface IconRepository extends JpaRepository<Icon, Integer>, JpaSpecificationExecutor<Icon> {

    Optional<Icon> findByIconCode(String iconCode);
}

