package com.bucwith.service.Community;

import com.bucwith.domain.account.User;
import com.bucwith.repository.user.UserRepository;
import com.bucwith.domain.community.Category;
import com.bucwith.domain.community.CommuCate;
import com.bucwith.domain.community.Community;
import com.bucwith.domain.community.Clike;
import com.bucwith.dto.community.*;
import com.bucwith.repository.community.CommentRepository;
import com.bucwith.repository.community.CommuCateRepository;
import com.bucwith.repository.community.CommunityRepository;
import com.bucwith.repository.community.ClikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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

    /**게시글 저장**/
    //@Transactional
    public Long commuSave(CommuSaveReqDto reqDto){
        User user  = userRepository.findById(reqDto.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + reqDto.getUser().getUserId()));

        /**게시글 저장**/
        Long commuId = communityRepository.save(reqDto.toEntity()).getCommuId();

        if(!CollectionUtils.isEmpty(reqDto.getCategory())){
            /**카테고리 저장**/
            SaveCategory(commuId, reqDto.getCategory());
        }
        return commuId;
    }

    /**카테고리 저장**/
    public void SaveCategory(Long commuId,List<Category> categoryList){
        Community entity  = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + commuId));

        for(Category category : categoryList){
            CommuCate commuCate =
                    CommuCate.builder()
                            .community(entity)
                            .category(category)
                            .build();
            commuCateRepository.save(commuCate);
        }
    }

    /**게시글 조회**/
    @Transactional(readOnly = true)
    public CommuResDto findCommuById(Long commuId){
        /**글 불러오기**/
        Community entity  = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + commuId));

        /**카테고리 불러오기**/
        List<CommuCate> categorys = commuCateRepository.findByCommunity(entity);
        List<Category> categoryList = new ArrayList<>();
        for (CommuCate commuCate: categorys){
            categoryList.add(commuCate.getCategory());
        }

        /**좋아요 개수**/
        Long LikeCnt = clikeRepository.CountByCommunity(entity.getCommuId());

        /**댓글 개수**/
        Long CommCnt = commentRepository.CountByCommunity(entity.getCommuId());

        return new CommuResDto(entity, categoryList, LikeCnt, CommCnt);
    }

    /**전체 글 조회**/
    @Transactional(readOnly = true)
    public List<CommuResDto> findCommuAllDesc(){
        /**커뮤니티 리스트 전체 조회**/
        List<Community> communities = communityRepository.findAllDesc();


        List<CommuResDto> commuList = new ArrayList<>();

        for (Community community : communities){
            /**좋아요 개수**/
            Long likeCnt = clikeRepository.CountByCommunity(community.getCommuId());
            /**댓글 개수**/
            Long commCnt = commentRepository.CountByCommunity(community.getCommuId());
            /**커뮤니티 카테고리 배열 생성**/
            List<CommuCate> categorys = commuCateRepository.findByCommunity(community);
            List<Category> categoryList = new ArrayList<>();
            for (CommuCate commuCate: categorys){
                categoryList.add(commuCate.getCategory());
            }
            /**보여줄 커뮤니티 객체 생성**/
            CommuResDto resDto = new CommuResDto(community, categoryList, likeCnt, commCnt);
            commuList.add(resDto);
        }

        return commuList;
    }

    /**댓글 저장**/
    @Transactional
    public Long commentSave(CommentSaveReqDto reqDto){

        Long commentId = commentRepository.save(reqDto.toEntity()).getComId();

        return commentId;
    }

    /**댓글 조회**/
    @Transactional(readOnly = true)
    public List<CommentResDto> findCommentAllDesc(Long commuId){
        return commentRepository.findAllDesc().stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }

    /**좋아요**/
    @Transactional
    public void commuLike(Long commuId, Long userId){
        /**커뮤니티, 작성자 확인**/
        Community community = communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id=" + commuId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));

        /**좋아요 여부 확인**/
        Optional<Clike> isLike = clikeRepository.findByCommunityAndUser(community, user);

        isLike.ifPresentOrElse(
                /**좋아요가 있다면 좋아요 삭제**/
                clike -> {
                    clikeRepository.delete(clike);
                },
                /**좋아요가 저장안되었다면 좋아요 등록**/
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
