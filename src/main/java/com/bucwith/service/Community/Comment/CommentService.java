package com.bucwith.service.Community.Comment;

import com.bucwith.domain.community.Category;
import com.bucwith.domain.community.Comment;
import com.bucwith.domain.community.CommuCate;
import com.bucwith.domain.community.Community;
import com.bucwith.dto.community.*;
import com.bucwith.repository.community.CommentRepository;
import com.bucwith.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @Transactional
    public Long commentSave(CommentSaveReqDto reqDto){
        Long commentId = commentRepository.save(reqDto.toEntity()).getComId();

        return commentId;
    }

    @Transactional(readOnly = true)
    public List<CommentAllResDto> findCommentAllDesc(Long commuId){
        List<Comment> comments = commentRepository.findCommentDesc(commuId);

        List<CommentAllResDto> commentAll = new ArrayList<>();

        for (Comment comment : comments){
            List<CommentResDto> commentResDtos = commentRepository.findReplyDesc(commuId, comment.getComId()).stream()
                    .map(CommentResDto::new)
                    .collect(Collectors.toList());
            CommentAllResDto resDto = new CommentAllResDto(comment, commentResDtos);
            commentAll.add(resDto);
        }

        return commentAll;
    }

    @Transactional
    public Long modifyComment(Long commentId, CommentModifyReqDto reqDto){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        comment.modify(reqDto.getContent(), reqDto.getSecret());

        return commentId;
    }

    @Transactional
    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        commentRepository.delete(comment);
    }
}
