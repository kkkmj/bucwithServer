package com.bucwith.domain.community;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    CC001("CC001","영화"), // 벅윗 파티원 모집

    CC002("CC002","여행"), //질문있어요

    CC003("CC003","대학합격"), // 추천해주세요
    CC004("CC004","미용/뷰티"), // 추천해주세요
    CC005("CC005","연애"), // 추천해주세요
    CC006("CC006","자격증"), // 추천해주세요
    CC007("CC007","공부"), // 추천해주세요
    CC008("CC008","다이어트"), // 추천해주세요
    CC009("CC009","음식"), // 추천해주세요
    CC010("CC010","동물"), // 추천해주세요
    CC011("CC011","게임"), // 벅윗 파티원 모집

    CC012("CC012","IT"), //질문있어요

    CC013("CC013","돈"), // 추천해주세요
    CC014("CC014","건강"), // 추천해주세요
    CC015("CC015","운동"), // 추천해주세요
    CC016("CC016","휴식/힐링"), // 추천해주세요
    CC017("CC017","외국어"), // 추천해주세요
    CC018("CC018","심리/마음"), // 추천해주세요
    CC019("CC019","운전"), // 추천해주세요
    CC020("CC020","취미"), // 추천해주세요
    CC021("CC021","기타"); // 추천해주세요

    private String code;
    private String desc;

    Category(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    // @RequestBody ENUM Parsing
    @JsonCreator
    public static Category from(String s) {
        return Category.valueOf(s);
    }
}

