package com.chaospro.rxmoviesdemo.model;

/**
 * Created by chaosboy on 2017/5/9.
 */

public enum MainTabType {

    GANK(0), INFO(1), ABOUT(2);

    private final Integer value;

    MainTabType(Integer type) {
        this.value = type;
    }

    public Integer getValue() {
        return value;
    }

    public static MainTabType of(String state) {
        return Enum.valueOf(MainTabType.class, state);
    }
}
