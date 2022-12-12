package com.bucwith.service.comment;

import com.bucwith.domain.comment.Comment;
import com.bucwith.domain.community.Community;
import com.bucwith.dto.comment.CommentAllResDto;
import com.bucwith.dto.comment.CommentModifyReqDto;
import com.bucwith.dto.comment.CommentResDto;
import com.bucwith.dto.comment.CommentSaveReqDto;
import com.bucwith.repository.comment.CommentRepository;
import com.bucwith.repository.community.CommunityRepository;
import com.bucwith.service.community.CommunityService;
import com.bucwith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final CommunityService communityService;
    private final UserService userService;

    @Transactional
    public Long commentSave(CommentSaveReqDto reqDto){
        communityRepository.updatePlusComment(reqDto.getCommuId());
        return commentRepository.save(reqDto.toEntity(communityService.getCommu(reqDto.getCommuId()), userService.getUser(reqDto.getUserId()))).getCommentId();
    }

    @Transactional(readOnly = true)
    public List<CommentAllResDto> findCommentAllDesc(Long commuId, Long userId){
        List<Comment> comments = commentRepository.findAllAsc(commuId);
        Community community = communityService.getCommu(commuId);
        return comments.stream().map(comment -> new CommentAllResDto(comment,
                comments.stream()
                        .filter(c -> c.getParentId().equals(comment.getCommentId()))
                        .map(CommentResDto::new)
                        //secret이고, 대댓글 작성자가 아니고, 댓글 작성자가 아니면 비밀댓글
                        .map(cDto->cDto.getSecret().equals(Boolean.TRUE)&&(!userId.equals(cDto.getUserId())&&!userId.equals(comment.getUser().getUserId()))?cDto.SecretContent():cDto)
                        .collect(Collectors.toList())))
                //secret이고, 댓글작성자가 아니고, 원글작성자가 아니면 비밀댓글
                .map(cADto -> cADto.getSecret().equals(Boolean.TRUE)&&(!userId.equals(cADto.getUserId())&&!userId.equals(community.getUser().getUserId()))?cADto.SecretContent():cADto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long modifyComment(Long commentId, CommentModifyReqDto reqDto){
        getComment(commentId).modify(reqDto.getContent(), reqDto.getSecret());
        return commentId;
    }

    @Transactional
    public void deleteComment(Long commentId){
        communityRepository.updateMinusComment(getComment(commentId).getCommunity().getCommuId());
        commentRepository.deleteById(commentId);
    }

    public Comment getComment(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
    }
}
