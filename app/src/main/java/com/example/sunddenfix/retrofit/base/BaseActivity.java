package com.example.sunddenfix.retrofit.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author wangchengmeng
 * @desc
 * @更新时间
 */
public abstract class BaseActivity<T extends IPrensenter> extends AppCompatActivity {

    protected T mIPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIPresenter = getPresenter();
    }

    protected abstract T getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        mIPresenter.detatchView();
        mIPresenter = null;
    }
}
