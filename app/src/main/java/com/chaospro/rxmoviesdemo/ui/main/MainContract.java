package com.chaospro.rxmoviesdemo.ui.main;

import com.chaospro.rxmoviesdemo.model.GankDataType;
import com.chaospro.rxmoviesdemo.model.MainTabType;
import com.chaospro.rxmoviesdemo.mvp.BasePresenter;
import com.chaospro.rxmoviesdemo.mvp.BaseView;

/**
 * Created by chaosboy on 2017/5/6.
 */

public interface MainContract {

    interface View extends BaseView {

        void switchTabFragment(MainTabType tabType, boolean isForce);

    }

    interface Presenter extends BasePresenter<View> {
        //void requestGankData(GankDataType type, int pageNum);


    }

}
