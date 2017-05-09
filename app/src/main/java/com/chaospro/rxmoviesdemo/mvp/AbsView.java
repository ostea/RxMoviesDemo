package com.chaospro.rxmoviesdemo.mvp;

/**
 * Created by chaosboy on 2017/5/9.
 */

public interface AbsView extends BaseView {

    void showWaitDialog();

    void hideWaitDialog();
}
