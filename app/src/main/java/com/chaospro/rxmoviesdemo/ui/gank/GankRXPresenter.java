package com.chaospro.rxmoviesdemo.ui.gank;

import com.chaospro.rxmoviesdemo.model.AndroidModel;
import com.chaospro.rxmoviesdemo.model.GankDataType;
import com.chaospro.rxmoviesdemo.model.GankModel;
import com.chaospro.rxmoviesdemo.model.IOSModel;
import com.chaospro.rxmoviesdemo.net.HttpManager;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by chaosboy on 2017/5/9.
 */

public class GankRXPresenter extends GankRxContract.Presenter {

    GankRxContract.View mGankView;

    HttpManager mHttpManager = HttpManager.getInstance();

    public GankRXPresenter(GankRxContract.View view) {
        this.mGankView = view;
    }

    @Override
    protected void start() {
        super.start();
    }

    @Override
    void requestGankData(GankDataType type, int pageNum) {
        if (mGankView == null)
            return;
        mGankView.showWaitDialog();
        switch (type) {
            case ANDROID:
                mHttpManager.getAndroidDataResult(new Consumer<List<AndroidModel>>() {
                    @Override
                    public void accept(List<AndroidModel> androidModels) throws Exception {
                        mGankView.updateformRequest(androidModels);
                    }
                }, pageNum);
                break;
            case IOS:
                mHttpManager.getIOSDataResult(new Consumer<List<IOSModel>>() {
                    @Override
                    public void accept(List<IOSModel> iosModels) throws Exception {

                    }
                }, pageNum);
                break;
            case GANK:
                mHttpManager.getGankDataResult(new Consumer<List<GankModel>>() {
                    @Override
                    public void accept(List<GankModel> gankModels) throws Exception {
                        mGankView.hideWaitDialog();
                        mGankView.updateGankDataformRequest(gankModels);
                    }
                }, mGankView.getPageNum());
                break;
            default:
                break;
        }
    }

    @Override
    void forceRefreshGankData(GankDataType type) {
        mGankView.resetPageNum();
        requestGankData(type, mGankView.getPageNum());
    }
}
