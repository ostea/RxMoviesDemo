package com.chaospro.rxmoviesdemo.ui.gank;

import android.view.View;
import android.widget.TextView;

import com.chaospro.rxmoviesdemo.R;
import com.chaospro.rxmoviesdemo.base.AbstractFragment;
import com.chaospro.rxmoviesdemo.event.ForceFreshEvent;
import com.chaospro.rxmoviesdemo.model.AndroidModel;
import com.chaospro.rxmoviesdemo.model.GankDataType;
import com.chaospro.rxmoviesdemo.model.GankModel;
import com.chaospro.rxmoviesdemo.utils.RxBus;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by chaosboy on 2017/5/9.
 */

public class GankRxFragment extends AbstractFragment<GankRXPresenter> implements GankRxContract.View {

    private TextView tv_content;


    private int mPageNum;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initPresenter() {
        presenter = new GankRXPresenter(this);
    }

    @Override
    protected void initView(View view) {
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText("Gank");
        if (presenter == null) {
            presenter = new GankRXPresenter(this);
        }
        presenter.start();
        RxBus.create().toObservable(ForceFreshEvent.class)
                .subscribe(new Consumer<ForceFreshEvent>() {
                    @Override
                    public void accept(ForceFreshEvent forceFreshEvent) throws Exception {
                        if (forceFreshEvent.getEventType() == ForceFreshEvent.START) {
                            presenter.forceRefreshGankData(GankDataType.GANK);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        presenter.requestGankData(GankDataType.GANK, mPageNum);
    }


    public static GankRxFragment newInstance() {
        GankRxFragment fragment = new GankRxFragment();
        return fragment;
    }

    @Override
    public void resetPageNum() {
        mPageNum = 0;
    }

    @Override
    public void updateformRequest(List<AndroidModel> androidModels) {
        tv_content.setText(androidModels.toString());
    }

    @Override
    public void updateGankDataformRequest(List<GankModel> gankModelList) {
        if (gankModelList != null) {
            tv_content.setText(gankModelList.toString());
        }
        RxBus.create().post(new ForceFreshEvent(ForceFreshEvent.STOP));
    }

    @Override
    public int getPageNum() {
        return mPageNum++;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
