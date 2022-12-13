package com.bucwith.controller.comment;

import com.bucwith.common.CommController;
import com.bucwith.common.config.JwtService;
import com.bucwith.common.exception.BaseException;
import com.bucwith.dto.comment.CommentModifyReqDto;
import com.bucwith.dto.comment.CommentSaveReqDto;
import com.bucwith.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController extends CommController {

    private final CommentService commentService;
    private final JwtService jwtService;

    /**
     * 댓글 등록!
     * @param reqDto (글번호, 댓글번호, 유저번호, 내용, 비밀여부)
     * 등록시 댓글개수 +1
     * @return 작성된 댓글 id
     */
    @PostMapping()
    public ResponseEntity commentSave(@Validated @RequestBody CommentSaveReqDto reqDto) throws BaseException {
        return SuccessReturn(commentService.commentSave(reqDto));
    }

    /**
     * 댓글 조회 리스트!
     * @param commuId
     * @return (댓글 번호, 글번호, 댓글 번호, 유저이름, 내용, 비밀여부, 등록날짜)
     */
    @GetMapping("/{commuId}")
    public ResponseEntity findCommentAll(@PathVariable Long commuId) throws BaseException {
        Long userId = jwtService.getUserId();
        return SuccessReturn(commentService.findCommentAllDesc(commuId, userId));
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param reqDto
     * @return 수정 댓글id
     */
    @PutMapping("/{commentId}")
    public ResponseEntity modifyComment(@PathVariable Long commentId, @Validated @RequestBody CommentModifyReqDto reqDto) {
        return SuccessReturn(commentService.modifyComment(commentId, reqDto));
    }

    /**
     * 댓글 삭제
     * @param commentId
     * 삭제 시 댓글개수 -1
     * 대댓글때문에 댓글 삭제시 내용만 삭제된 댓글입니다. 로 변경
     * @return 삭제된 댓글
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return SuccessReturn(commentId);
    }
}
