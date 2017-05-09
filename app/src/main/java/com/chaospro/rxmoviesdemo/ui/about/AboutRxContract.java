package com.chaospro.rxmoviesdemo.ui.about;

import com.chaospro.rxmoviesdemo.mvp.AbsPresenter;
import com.chaospro.rxmoviesdemo.mvp.BaseView;

/**
 * Created by chaosboy on 2017/5/9.
 */

public interface AboutRxContract {

    interface View extends BaseView {

    }

    abstract class Presenter extends AbsPresenter<View>{

    }

}
