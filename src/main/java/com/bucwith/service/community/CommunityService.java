package com.bucwith.service.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.community.*;
import com.bucwith.repository.user.UserRepository;
import com.bucwith.dto.community.*;
import com.bucwith.repository.community.CommentRepository;
import com.bucwith.repository.community.CommuCateRepository;
import com.bucwith.repository.community.CommunityRepository;
import com.bucwith.repository.community.ClikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ClikeRepository clikeRepository;
    private final CommuCateRepository commuCateRepository;

    //@Transactional
    public Long commuSave(CommuSaveReqDto reqDto){
        User user  = userRepository.findById(reqDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + reqDto.getUserId()));

        Long commuId = communityRepository.save(reqDto.toEntity(user)).getCommuId();

        if(!CollectionUtils.isEmpty(reqDto.getCategory())){
            SaveCategory(commuId, reqDto.getCategory());
        }
        return commuId;
    }

    public void SaveCategory(Long commuId,List<Category> categoryList){
        Community entity  = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commuId));

        for(Category category : categoryList){
            CommuCate commuCate =
                    CommuCate.builder()
                            .community(entity)
                            .category(category)
                            .build();
            commuCateRepository.save(commuCate);
        }
    }

    @Transactional(readOnly = true)
    public CommuResDto findCommuById(Long userId, Long commuId){
        Community entity  = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commuId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));

        List<Category> categorys = commuCateRepository.findByCommunity(entity).stream().map(CommuCate::getCategory).collect(Collectors.toList());
        Long LikeCnt = clikeRepository.CountByCommunity(entity.getCommuId());
        Long CommentCnt = commentRepository.CountByCommunity(entity.getCommuId());
        Boolean isLike = clikeRepository.existsByCommunityAndUser(entity, user);


        return new CommuResDto(entity, categorys, LikeCnt, CommentCnt, isLike );
    }

    @Transactional(readOnly = true)
    public List<CommuResDto> findCommuAllDesc(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));
        List<Community> communities = communityRepository.findAllDesc();
        List<CommuResDto> commus = new ArrayList<>();
        List<CommuCate> categorys = commuCateRepository.findAll();

        for (Community community : communities){
            Long likeCnt = clikeRepository.CountByCommunity(community.getCommuId());
            Long commentCnt = commentRepository.CountByCommunity(community.getCommuId());
            List<Category> categoryList = categorys.stream()
                    .filter(c -> c.getCommunity().getCommuId().equals(community.getCommuId()))
                    .map(CommuCate::getCategory)
                    .collect(Collectors.toList());
            Boolean isLike = clikeRepository.existsByCommunityAndUser(community, user);

            CommuResDto resDto = new CommuResDto(community, categoryList, likeCnt, commentCnt, isLike);
            commus.add(resDto);
        }

        return commus;
    }

    @Transactional
    public Long modifyCommu(Long commuId, CommuModifyReqDto reqDto){
        Community community = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commuId));
        community.modify(reqDto.getContent(), reqDto.getType(), reqDto.getParty());

        List<Category> categorys = commuCateRepository.findByCommunity(community).stream().map(CommuCate::getCategory).collect(Collectors.toList());
        if(!Objects.equals(categorys, reqDto.getCategory())){
            commuCateRepository.deleteAllInBatch(community.getCommuCates());
            SaveCategory(commuId, reqDto.getCategory());
        }

        return commuId;
    }

    @Transactional
    public void deleteCommu(Long commuId){
        Community community = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commuId));
        communityRepository.delete(community);
    }

    //TODO 조회수 중복방지를 할지 말지?
    @Transactional
    public int updateView(Long commuId){
        return communityRepository.updateView(commuId);
    }

    @Transactional
    public Long commentSave(CommentSaveReqDto reqDto){
        User user = userRepository.findById(reqDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + reqDto.getUserId()));
        Community community = communityRepository.findById(reqDto.getCommuId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + reqDto.getCommuId()));
        Long commentId = commentRepository.save(reqDto.toEntity(community, user)).getCommentId();

        return commentId;
    }

    // TODO DB설계를 바꿀지 고민
    @Transactional(readOnly = true)
    public List<CommentAllResDto> findCommentAllDesc(Long commuId){
        List<Comment> comments = commentRepository.findCommentAsc(commuId);
        List<Comment> replys = commentRepository.findReplysAsc(commuId);
        List<CommentAllResDto> commentAll = new ArrayList<>();

        for (Comment comment : comments){
/*            List<CommentResDto> commentResDtos = commentRepository.findReplyAsc(commuId, comment.getCommentId()).stream()
                    .map(CommentResDto::new)
                    .collect(Collectors.toList());*/
            List<CommentResDto> commentResDtos = replys.stream()
                    .filter(c -> c.getParentId().equals(comment.getCommentId()))
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



    @Transactional
    public void commuLike(Long commuId, Long userId){
        Community community = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id=" + commuId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));

        Optional<Clike> isLike = clikeRepository.findByCommunityAndUser(community, user);

        //좋아요가 이미 있을 시 delete하고(void) 없으면 객체를 return하기 때문에 이게 최선일듯?
        isLike.ifPresentOrElse(
                clike -> {
                    clikeRepository.delete(clike);
                },
                () -> {
                    Clike like = Clike.builder()
                            .community(community)
                            .user(user)
                            .build();
                    clikeRepository.save(like);
                }
        );
    }
}
