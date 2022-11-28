package com.bucwith.controller.Community.Comment;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.exception.BaseException;
import com.bucwith.dto.community.*;
import com.bucwith.service.Community.Comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommentController extends CommController {
    private final JwtService jwtService;
    private final CommentService commentService;

    /**
     * 댓글 등록!
     * @param commuId
     * @param reqDto (글번호, 댓글번호, 유저번호, 내용, 비밀여부)
     * @return 작성된 댓글 id
     */
    @PostMapping("/{commuId}/comment")
    public ResponseEntity commentSave(@PathVariable Long commuId, @Validated @RequestBody CommentSaveReqDto reqDto) throws BaseException {
        Long commentId = commentService.commentSave(reqDto);
        return SuccessReturn(commentId);
    }

    /**
     * 댓글 조회 리스트!
     * @param commuId
     * @return (댓글 번호, 글번호, 댓글 번호, 유저이름, 내용, 비밀여부, 등록날짜)
     */
    @GetMapping("/{commuId}/comment")
    public ResponseEntity findCommentAll(@PathVariable Long commuId){
        List<CommentAllResDto> comment = commentService.findCommentAllDesc(commuId);
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
        Long modifyId = commentService.modifyComment(commentId, reqDto);
        return SuccessReturn(modifyId);
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @return 삭제된 댓글 id
     */
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return SuccessReturn(commentId);
    }
}
