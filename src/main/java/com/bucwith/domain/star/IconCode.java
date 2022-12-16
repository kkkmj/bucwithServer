package com.bucwith.domain.star;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum IconCode {

    CS001P("CS001P","Star","Pink"), // 꾸준히
    CS001B("CS001B","Star","Blue"), //일년안에
    CS001Y("CS001Y","Star","Yellow"), // 오랫동안
    CS002P("CS002P","Rocket","Pink"), // 오랫동안
    CS002B("CS002B","Rocket","Blue"), // 오랫동안
    CS002Y("CS002Y","Rocket","Yellow"), // 오랫동안
    CS003P("CS003P","Planet","Pink"), // 오랫동안
    CS003B("CS003B","Planet","Blue"), // 오랫동안
    CS003Y("CS003Y","Planet","Yellow"), // 오랫동안
    CS004P("CS004P","Shooting Star","Pink"), // 오랫동안
    CS004B("CS004B","Shooting Star","Blue"), // 오랫동안
    CS004Y("CS004Y","Shooting Star","Yellow"); // 오랫동안

    private String code;
    private String icon;
    private String color;

    IconCode(String code, String icon, String color) {
        this.code = code;
        this.icon = icon;
        this.color = color;
    }

    public String getCode() {
        return this.code;
    }

    // @RequestBody ENUM Parsing
    @JsonCreator
    public static IconCode from(String s) {
        return IconCode.valueOf(s);
    }
}