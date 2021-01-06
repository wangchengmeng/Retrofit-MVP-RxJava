package com.example.sunddenfix.retrofit.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunddenfix.retrofit.R;
import com.example.sunddenfix.retrofit.base.BaseActivity;
import com.example.sunddenfix.retrofit.config.Constant;
import com.example.sunddenfix.retrofit.bean.CountryModel;
import com.example.sunddenfix.retrofit.presenter.MainPresenter;
import com.example.sunddenfix.retrofit.ui.adapter.CountryAdapter;
import com.example.sunddenfix.retrofit.viewIm.MainView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    private List<CountryModel> mCountryList;
    private CountryAdapter mCountryAdapter;
    private RecyclerView mRvCommonList;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCountryAdapter = new CountryAdapter(this, mCountryList);
        mRvCommonList = findViewById(R.id.rv_commonList);
        mSmartRefreshLayout = findViewById(R.id.srl_freshList);
        mRvCommonList.setLayoutManager(new LinearLayoutManager(this));
        mRvCommonList.setAdapter(mCountryAdapter);

        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIPresenter.getCountry("21.22.11.33", Constant.PULL_DOWN_FRESH);
            }
        });

        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIPresenter.getCountry("21.22.11.33", Constant.PULL_UP_LOAD);
            }
        });
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void getCountrySuccess(List<CountryModel> model, int status) {
        //请求成功 更新ui
        if (status == Constant.PULL_DOWN_FRESH) {
            mCountryList.clear();
        }
        mCountryList.addAll(model);
        if (status == Constant.PULL_DOWN_FRESH) {
            mCountryAdapter.notifyDataSetChanged();
        } else {
            mCountryAdapter.notifyItemInserted(mCountryList.size() - 1);
        }
    }

    @Override
    public void refreshOrDisLayout(int status) {
        if (status == Constant.PULL_DOWN_FRESH) {
            //下拉刷新
            mSmartRefreshLayout.finishRefresh();
        } else {
            //上拉加载
            mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void loadNorMoreData() {
        mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
    }
}
