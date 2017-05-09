package com.chaospro.rxmoviesdemo.app;

import android.app.Application;

import com.chaospro.rxmoviesdemo.utils.RxBus;

/**
 * Created by chaosboy on 2017/5/8.
 */

public class RxApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RxBus.create();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }
}
