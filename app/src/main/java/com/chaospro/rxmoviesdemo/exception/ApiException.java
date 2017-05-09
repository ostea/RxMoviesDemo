package com.chaospro.rxmoviesdemo.exception;

/**
 * Created by chaosboy on 2017/5/6.
 */

public class ApiException extends RuntimeException {


    public static final int USER_NOT_EXIST = 100;

    public static final int WRONG_PASSWORD = 101;

    public ApiException(int errorCode) {
        this(getApiExceptionMessage(errorCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "user not exit";
                break;
            case WRONG_PASSWORD:
                message = "pwd error";
                break;
            default:
                message = "unknown error";

        }
        return message;
    }
}
