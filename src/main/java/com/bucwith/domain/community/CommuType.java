package com.bucwith.domain.community;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CommuType {
    CT001("CT001","벅윗 파티원 모집"), // 벅윗 파티원 모집

    CT002("CT002","질문있어요"), //질문있어요

    CT003("CT003","추천해주세요"); // 추천해주세요

    private String code;
    private String desc;

    CommuType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    // @RequestBody ENUM Parsing
    @JsonCreator
    public static CommuType from(String s) {
        return CommuType.valueOf(s);
    }
}
