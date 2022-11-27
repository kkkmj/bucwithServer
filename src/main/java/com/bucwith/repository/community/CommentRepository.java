package com.bucwith.repository.community;

import com.bucwith.domain.community.Comment;
import com.bucwith.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c ORDER BY c.comId DESC")
    List<Comment> findAllDesc();

    @Query("SELECT COUNT( c ) FROM Comment c WHERE c.community.commuId = :commuId")
    Long CountByCommunity(@Param("commuId") Long commuId);


}
