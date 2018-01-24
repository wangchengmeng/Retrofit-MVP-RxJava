package com.example.sunddenfix.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sunddenfix.retrofit.base.BaseActivity;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.presenter.MainPresenter;
import com.example.sunddenfix.retrofit.viewIm.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    private int looper = 0;

    private TextView mTvResult;
    private TextView mTvFlowableResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getMainData();
    }

    private void initViews() {
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mTvFlowableResult = (TextView) findViewById(R.id.tv_flowable_result);
    }

    private void getMainData() {
        mIPresenter.getCountry("21.22.11.33");

        mIPresenter.getCountry2("21.22.11.33");
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    public void updateText(CountryModel model) {
        mTvResult.setText(model.getCountry() + ":" + model.getIp());
        mTvFlowableResult.setText(model.getCountry() + ":" + model.getIp());
    }
}
