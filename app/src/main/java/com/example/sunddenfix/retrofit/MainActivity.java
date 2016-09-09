package com.example.sunddenfix.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sunddenfix.retrofit.base.BaseActivity;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.presenter.MainPresenter;
import com.example.sunddenfix.retrofit.viewIm.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_name);
        getMainData();
    }

    private void getMainData() {
        mIPrensenter.getCountry("21.22.11.33");
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    public void updateText(CountryModel model) {
        mTextView.setText(model.getCountry() + ":" + model.getIp());
    }
}
