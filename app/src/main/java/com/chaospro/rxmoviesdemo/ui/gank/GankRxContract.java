package com.chaospro.rxmoviesdemo.ui.gank;

import com.chaospro.rxmoviesdemo.model.AndroidModel;
import com.chaospro.rxmoviesdemo.model.GankDataType;
import com.chaospro.rxmoviesdemo.model.GankModel;
import com.chaospro.rxmoviesdemo.mvp.AbsPresenter;
import com.chaospro.rxmoviesdemo.mvp.AbsView;

import java.util.List;

/**
 * Created by chaosboy on 2017/5/9.
 */

public interface GankRxContract {

    interface View extends AbsView {
        void resetPageNum();

        void updateformRequest(List<AndroidModel> androidModels);

        void updateGankDataformRequest(List<GankModel> androidModels);

        int getPageNum();
    }

    public abstract class Presenter extends AbsPresenter<View> {
        abstract void requestGankData(GankDataType type, int pageNum);

        abstract void forceRefreshGankData(GankDataType type);
    }

}
