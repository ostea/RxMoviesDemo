package com.chaospro.rxmoviesdemo.event;

/**
 * Created by chaosboy on 2017/5/9.
 */

public class ForceFreshEvent extends BaseEvent {
    public static final int START = 0x123;
    public static final int STOP = 0x1234;

    public ForceFreshEvent(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
