package com.bucwith.repository.community;

import com.bucwith.domain.community.CommuCate;
import com.bucwith.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuCateRepository extends JpaRepository<CommuCate, Long> {
    List<CommuCate> findByCommunity(Community community);

}
