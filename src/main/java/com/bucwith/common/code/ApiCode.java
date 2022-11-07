package com.bucwith.common.code;

public enum ApiCode {

    // -------------------------COMMON-------------------------
    SUCCESS(200, "SUCCESS"), //200

    PARAM_ERROR(300, "PARAM_ERROR"),

    // DB ERROR
    DB_FAIL(301, "DB ERROR"),
    NOT_FOUND_DATA(302, "DB NOT_FOUND_DATA"),

    // ------------------------FUNCTION----------------------------
    
    UNKNOWN_ERROR(1000, "알수 없는 오류");


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