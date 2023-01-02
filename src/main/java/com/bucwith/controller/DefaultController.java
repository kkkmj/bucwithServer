package com.bucwith.controller;


import com.bucwith.common.CommController;
import com.bucwith.dto.bucket.BucketReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
public class DefaultController extends CommController {

    /*
     * (" / ") Error handler
     */
    @RequestMapping("/")
    public ResponseEntity defaultController(@Validated @RequestBody BucketReqDto reqDto) {
        return SuccessReturn();
    }
}