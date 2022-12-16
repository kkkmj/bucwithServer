package com.bucwith.common.code;

public enum ApiCode {

    // -------------------------COMMON-------------------------
    SUCCESS(200, "SUCCESS"), //200

    PARAM_ERROR(300, "PARAM_ERROR"),

    // DB ERROR
    DB_FAIL(301, "DB ERROR"),
    NOT_FOUND_DATA(302, "DB NOT_FOUND_DATA"),

    EMPTY_JWT(2001, "JWT를 입력해주세요."),
    INVALID_JWT( 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(2003,"권한이 없는 유저의 접근입니다."),

    // ------------------------FUNCTION----------------------------
    
    UNKNOWN_ERROR(1000, "알수 없는 오류"),
    EXPIRED_REFRESH_TOKEN(1003, "만료된 refresh 토큰입니다. 다시 로그인해주세요."),
    WRONG_TYPE_TOKEN(1004, "변조된 토큰입니다."),
    EXPIRED_TOKEN(1005, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(1006, "변조된 토큰입니다."),
    ACCESS_DENIED(1007, "권한이 없습니다."),
    WRONG_TOKEN(1008, "잘못된 토큰입니다.");


    private int code;
    private String msg;

    ApiCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static ApiCode CODE(int intCode) {
        ApiCode apiCode = null;
        for (ApiCode code : ApiCode.values()) {
            if (intCode == code.getCode()) {
                apiCode = code;
                break;
            }
        }

        if (apiCode == null) {
            apiCode = ApiCode.SUCCESS;
        }

        return apiCode;
    }
}