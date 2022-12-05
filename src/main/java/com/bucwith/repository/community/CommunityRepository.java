package com.bucwith.repository.community;

import com.bucwith.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("SELECT c FROM Community c ORDER BY c.commuId DESC")
    List<Community> findAllDesc();

    @Modifying
    @Query("update Community c set c.viewCnt = c.viewCnt + 1 where c.commuId = :commuId")
    int updateView(Long commuId);
}
