package com.bucwith.repository.commuCategory;

import com.bucwith.domain.commuCategory.CommuCate;
import com.bucwith.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuCateRepository extends JpaRepository<CommuCate, Long> {
    List<CommuCate> findByCommunity(Community community);

}
