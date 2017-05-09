package com.chaospro.rxmoviesdemo.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by chaosboy on 2017/5/8.
 */

public class RxBus {

    private final PublishSubject<Object> bus = PublishSubject.create();

    private RxBus() {
    }

    private static volatile RxBus rxBus;

    public static RxBus create() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                    return rxBus;
                }
            }
        }
        return rxBus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> clazzType) {
        return bus.ofType(clazzType);
    }
}
