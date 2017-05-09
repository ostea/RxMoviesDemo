package com.chaospro.rxmoviesdemo.ui.main;

/**
 * Created by chaosboy on 2017/5/6.
 */

public class MainPresenter implements MainContract.Presenter {


    private MainContract.View mView;


    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }
// TODO: 2017/5/9 把MainActivity里面的相关逻辑整合到P层

//    @Override
//    public void requestGankData(GankDataType type, int pageNum) {
//
//        if (GankDataType.ANDROID.equals(type)) {
//
//        } else if (GankDataType.IOS.equals(type)) {
//
//        } else if (GankDataType.GANK.equals(type)) {
//
//        }
//    }
}
