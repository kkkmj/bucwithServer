package com.bucwith.controller.Community;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.exception.BaseException;
import com.bucwith.dto.community.CommentResDto;
import com.bucwith.dto.community.CommentSaveReqDto;
import com.bucwith.dto.community.CommuResDto;
import com.bucwith.dto.community.CommuSaveReqDto;
import com.bucwith.service.Community.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**커뮤니티 controller
 * jwt미완+테스트의 편의성으로 인해
 * 작성 시 유저 검증은 아직 하지 않았음
 * jwt 완성 시 추가 예정**/
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
    public ResponseEntity commuSave(@RequestBody CommuSaveReqDto reqDto) throws BaseException {
        //Long userId=jwtService.getUserId();
        Long commuId = communityService.commuSave(reqDto);
        return SuccessReturn(commuId);
    }

    /**
     * 게시글 리스트 조회!
     * @return (글번호, 유저이름, 내용, 타입, 카테고리 배열, 파티원수, 좋아요 수, 댓글 수, 작성시간)
     */
    @GetMapping()
    public ResponseEntity findCommuAll(){
        List<CommuResDto> commu = communityService.findCommuAllDesc();
        return SuccessReturn(commu);
    }

    /**
     * 특정 게시글 조회!
     * @param commuId
     * @return (글번호, 유저이름, 내용, 타입, 카테고리 배열, 파티원수, 좋아요 수, 댓글 수, 작성시간)
     */
    @GetMapping("/{commuId}")
    public ResponseEntity findCommuById(@PathVariable Long commuId){
        CommuResDto resDto = communityService.findCommuById(commuId);
        return SuccessReturn(resDto);
    }

    /**
     * 댓글 등록!
     * @param commuId
     * @param reqDto (글번호, 댓글번호, 유저번호, 내용, 비밀여부)
     * @return 작성된 댓글 id
     */
    @PostMapping("/{commuId}/comment")
    public ResponseEntity commentSave(@PathVariable Long commuId, @RequestBody CommentSaveReqDto reqDto) throws BaseException {
        //Long userId=jwtService.getUserId();

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
        List<CommentResDto> comment = communityService.findCommentAllDesc(commuId);
        return SuccessReturn(comment);
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
        //Long userId = 4L;
        communityService.commuLike(commuId,userId);
        return SuccessReturn();
    }




}
