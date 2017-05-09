package com.chaospro.rxmoviesdemo.mvp;

import com.chaospro.rxmoviesdemo.mvp.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by chaosboy on 2017/5/8.
 */

public abstract class AbsPresenter<V extends BaseView> {
    public V view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void start() {

    }

    public void attachModelView(V v) {
        this.view = v;
        start();
    }

   public void detachModelView() {
        this.view = null;
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public Disposable add(Disposable disposable) {
        compositeDisposable.add(disposable);
        return disposable;
    }
}
