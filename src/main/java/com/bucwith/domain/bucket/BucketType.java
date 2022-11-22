package com.bucwith.domain.bucket;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BucketType {

    BT001("BT001","꾸준히"), // 꾸준히
    BT002("BT002","일년안에"), //일년안에
    BT003("BT003","오랫동안"); // 오랫동안
    
    private String code;
    private String desc;

    BucketType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    // @RequestBody ENUM Parsing
    @JsonCreator
    public static BucketType from(String s) {
        return BucketType.valueOf(s);
    }
}