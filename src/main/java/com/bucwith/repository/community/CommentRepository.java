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

    @Query("SELECT c FROM Comment c WHERE c.community.commuId = :commuId AND c.replyId IS NULL ORDER BY c.comId ASC")
    List<Comment> findCommentDesc(@Param("commuId") Long commuId);

    @Query("SELECT c FROM Comment c WHERE c.community.commuId = :commuId AND c.replyId = :replyId ORDER BY c.comId ASC")
    List<Comment> findReplyDesc(@Param("commuId") Long commuId, @Param("replyId") Long replyId);

    @Query("SELECT c FROM Comment c WHERE c.community.commuId = :commuId ORDER BY c.comId ASC")
    List<Comment> findAllDesc(@Param("commuId") Long commuId);

    @Query("SELECT COUNT( c ) FROM Comment c WHERE c.community.commuId = :commuId")
    Long CountByCommunity(@Param("commuId") Long commuId);


}
