package com.chaospro.rxmoviesdemo.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaospro.rxmoviesdemo.mvp.AbsPresenter;
import com.chaospro.rxmoviesdemo.mvp.AbsView;
import com.chaospro.rxmoviesdemo.utils.ClassUtils;

/**
 * Created by chaosboy on 2017/5/8.
 */

public abstract class AbstractFragment<P extends AbsPresenter> extends Fragment implements AbsView {

    public P presenter;
    protected ProgressDialog waitDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        presenter = ClassUtils.getT(this, 0);
        if (presenter == null) {
            initPresenter();
        }
        presenter.attachModelView(this);
        initView(view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachModelView();
        }
    }

    @Override
    public void showWaitDialog() {
        if (!Thread.currentThread().getName().equals("main")) {
            new Handler(Looper.getMainLooper()).post(new DialogRunnable());
        } else {
            new DialogRunnable().run();
        }
    }

    @Override
    public void hideWaitDialog() {
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }

    }

    protected abstract int getLayoutId();

    protected abstract void initPresenter();

    protected abstract void initView(View view);


    private class DialogRunnable implements Runnable {
        @Override
        public void run() {
            if (waitDialog == null) {
                waitDialog = new ProgressDialog(getActivity());
                waitDialog.setMessage("加载数据..");
            }
            waitDialog.show();
        }
    }
}
