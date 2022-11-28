package com.bucwith.controller.Community;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.exception.BaseException;
import com.bucwith.dto.community.*;
import com.bucwith.service.Community.CommunityService;
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
     * @param commuId
     * @return (글번호, 유저이름, 내용, 타입, 카테고리 배열, 파티원수, 좋아요 수, 댓글 수, 작성시간, 좋아요 유무)
     */
    @GetMapping("/{commuId}")
    public ResponseEntity findCommuById(@PathVariable Long commuId) throws BaseException {
        Long userId=jwtService.getUserId();
        CommuResDto resDto = communityService.findCommuById(userId, commuId);
        return SuccessReturn(resDto);
    }

    @PutMapping("/{commuId}")
    public ResponseEntity modifyCommu(@PathVariable Long commuId, @Validated @RequestBody CommuModifyReqDto reqDto) {
        Long modifyId = communityService.modifyCommu(commuId, reqDto);
        return SuccessReturn(modifyId);
    }

    @DeleteMapping("/{commuId}")
    public ResponseEntity deleteCommu(@PathVariable Long commuId){
        communityService.deleteCommu(commuId);
        return SuccessReturn(commuId);
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
