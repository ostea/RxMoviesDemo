package com.chaospro.rxmoviesdemo.ui.info;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chaospro.rxmoviesdemo.R;
import com.chaospro.rxmoviesdemo.base.AbstractFragment;

/**
 * Created by chaosboy on 2017/5/9.
 */

public class InfoRxFragment extends AbstractFragment<InfoRXPresenter> implements InfoRxContract.View {

    private TextView tv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText("Info");

    }

    public static InfoRxFragment newInstance() {
        InfoRxFragment fragment = new InfoRxFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
