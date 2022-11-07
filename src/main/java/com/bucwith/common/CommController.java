package com.bucwith.common;

import com.bucwith.common.code.ApiCode;
import com.bucwith.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public class CommController {

    public ResponseEntity SuccessReturn(Object data) {
        return ResponseEntity.ok().body(
                ResponseDTO.builder().code(ApiCode.SUCCESS.getCode()).codeMsg(ApiCode.SUCCESS.getMsg()).data(data).build());
    }

    public ResponseEntity SuccessReturn() {
        return ResponseEntity.ok().body(
                ResponseDTO.builder().code(ApiCode.SUCCESS.getCode()).codeMsg(ApiCode.SUCCESS.getMsg()).build());
    }

    public ResponseEntity ErrorReturn(ApiCode apiCode) {
        return ResponseEntity.ok().body(
                ResponseDTO.builder().code(apiCode.getCode()).codeMsg(apiCode.getMsg()).build());
    }

    public ResponseEntity ErrorReturn(ApiCode apiCode, Object data) {
        return ResponseEntity.ok().body(
                ResponseDTO.builder().code(apiCode.getCode()).codeMsg(apiCode.getMsg()).data(data).build());
    }
}
