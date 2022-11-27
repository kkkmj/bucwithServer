package com.bucwith.repository.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.community.Community;
import com.bucwith.domain.community.Clike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClikeRepository extends JpaRepository<Clike, Long> {
    Optional<Clike> findByCommunityAndUser(Community community, User user);

    @Query("SELECT COUNT(l) FROM Clike l WHERE l.community.commuId = :commuId")
    Long CountByCommunity(@Param("commuId") Long commuId);

}
