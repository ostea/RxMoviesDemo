package com.chaospro.rxmoviesdemo.model;

/**
 * Created by chaosboy on 2017/5/9.
 */

public enum GankDataType {
    ANDROID("ANDROID"), IOS("IOS"), GANK("GANK");

    GankDataType(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    public static GankDataType of(String state) {
        return Enum.valueOf(GankDataType.class, state);
    }

}
