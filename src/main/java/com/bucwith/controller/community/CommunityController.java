package com.bucwith.controller.community;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.exception.BaseException;
import com.bucwith.dto.community.*;
import com.bucwith.service.community.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController extends CommController {
    private final CommunityService communityService;
    private final JwtService jwtService;

    /**
     * 게시글 저장!
     * @param reqDto (유저id, 내용, 글타입, 카테고리 리스트, 파티원수)
     * @return 등록한 글의 번호 반환
     */
    @PostMapping()
    public ResponseEntity commuSave(@Validated @RequestBody CommuSaveReqDto reqDto) throws BaseException {
        Long commuId = communityService.commuSave(reqDto);
        return SuccessReturn(commuId);
    }

    /**
     * 게시글 리스트 조회!
     * @return (글번호, 유저이름, 내용, 타입, 카테고리 배열, 파티원수, 좋아요 수, 댓글 수, 작성시간, 좋아요 유무)
     */
    @GetMapping()
    public ResponseEntity findCommuAll() throws BaseException {
        Long userId=jwtService.getUserId();
        List<CommuResDto> commu = communityService.findCommuAllDesc(userId);
        return SuccessReturn(commu);
    }

    /**
     * 특정 게시글 조회!
     * 조회수 증가
     * @param commuId
     * @return (글번호, 유저이름, 내용, 타입, 카테고리 배열, 파티원수, 좋아요 수, 댓글 수, 작성시간, 좋아요 유무)
     */
    @GetMapping("/{commuId}")
    public ResponseEntity findCommuById(@PathVariable Long commuId) throws BaseException {
        Long userId=jwtService.getUserId();
        CommuResDto resDto = communityService.findCommuById(userId, commuId);
        communityService.updateView(commuId);
        return SuccessReturn(resDto);
    }

    /**
     * 게시글 수정
     * @param commuId
     * @param reqDto
     * @return 수정id
     */
    @PutMapping("/{commuId}")
    public ResponseEntity modifyCommu(@PathVariable Long commuId, @Validated @RequestBody CommuModifyReqDto reqDto) {
        Long modifyId = communityService.modifyCommu(commuId, reqDto);
        return SuccessReturn(modifyId);
    }

    /**
     * 커뮤니티 삭제
     * @param commuId
     * @return 삭제된 id
     */
    @DeleteMapping("/{commuId}")
    public ResponseEntity deleteCommu(@PathVariable Long commuId){
        communityService.deleteCommu(commuId);
        return SuccessReturn(commuId);
    }

    /**
     * 댓글 등록!
     * @param commuId
     * @param reqDto (글번호, 댓글번호, 유저번호, 내용, 비밀여부)
     * @return 작성된 댓글 id
     */
    @PostMapping("/{commuId}/comment")
    public ResponseEntity commentSave(@PathVariable Long commuId, @Validated @RequestBody CommentSaveReqDto reqDto) throws BaseException {
        Long commentId = communityService.commentSave(reqDto);
        return SuccessReturn(commentId);
    }

    /**
     * 댓글 조회 리스트!
     * @param commuId
     * @return (댓글 번호, 글번호, 댓글 번호, 유저이름, 내용, 비밀여부, 등록날짜)
     */
    @GetMapping("/{commuId}/comment")
    public ResponseEntity findCommentAll(@PathVariable Long commuId){
        List<CommentAllResDto> comment = communityService.findCommentAllDesc(commuId);
        return SuccessReturn(comment);
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param reqDto
     * @return 수정 댓글id
     */
    @PutMapping("/comment/{commentId}")
    public ResponseEntity modifyComment(@PathVariable Long commentId, @Validated @RequestBody CommentModifyReqDto reqDto) {
        Long modifyId = communityService.modifyComment(commentId, reqDto);
        return SuccessReturn(modifyId);
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @return 삭제된 댓글 id
     */
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        communityService.deleteComment(commentId);
        return SuccessReturn(commentId);
    }



    /**
     * 게시글 좋아요!
     * @param commuId
     * @return 게시글 좋아요 데이터 없을 시 좋아요 등록/ 있을 시 좋아요 취소
     * @throws BaseException
     */
    @PostMapping("/{commuId}/like")
    public ResponseEntity commuLike(@PathVariable Long commuId) throws BaseException {
        Long userId=jwtService.getUserId();
        communityService.commuLike(commuId,userId);
        return SuccessReturn();
    }




}
