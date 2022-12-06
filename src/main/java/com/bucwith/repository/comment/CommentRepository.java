package com.bucwith.repository.comment;

import com.bucwith.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //대댓글이 아닌 댓글 전체 조회
    @Query("SELECT c FROM Comment c WHERE c.community.commuId = :commuId AND c.parentId = 0 ORDER BY c.commentId ASC")
    List<Comment> findCommentAsc(@Param("commuId") Long commuId);

    //댓글에 해당하는 대댓글 조회
    @Query("SELECT c FROM Comment c WHERE c.community.commuId = :commuId AND c.parentId = :parentId ORDER BY c.commentId ASC")
    List<Comment> findReplyAsc(@Param("commuId") Long commuId, @Param("parentId") Long parentId);

    //대댓글 전체 조회
    @Query("SELECT c FROM Comment c WHERE c.community.commuId = :commuId AND c.parentId <> 0 ORDER BY c.commentId ASC")
    List<Comment> findReplysAsc(@Param("commuId") Long commuId);

    //댓글 전체 조회
    @Query("SELECT c FROM Comment c WHERE c.community.commuId = :commuId ORDER BY c.commentId ASC")
    List<Comment> findAllAsc(@Param("commuId") Long commuId);

    @Query("SELECT COUNT( c ) FROM Comment c WHERE c.community.commuId = :commuId")
    Long CountByCommunity(@Param("commuId") Long commuId);


}
