package com.example.sunddenfix.retrofit.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.sunddenfix.retrofit.base.BasePrensenter;
import com.example.sunddenfix.retrofit.business.MainReq;
import com.example.sunddenfix.retrofit.config.ActionResult;
import com.example.sunddenfix.retrofit.config.Constant;
import com.example.sunddenfix.retrofit.bean.CountryModel;
import com.example.sunddenfix.retrofit.utils.rx.RxSubscriber;
import com.example.sunddenfix.retrofit.viewIm.MainView;

import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author wangchengmeng
 */
public class MainPresenter extends BasePrensenter<MainView> {
    private int mCurrentPage = 1;

    public MainPresenter(Activity activity, MainView view) {
        super(activity, view);
    }

    /**
     * 一般返回数据格式：
     * code：200
     * data：{} 或者[]
     * message：返回信息
     */
    public void getCountry(String ip, int status) {
        if (status == Constant.PULL_UP_LOAD && mCurrentPage == 1) {
            mIView.refreshOrDisLayout(status);
            return;
        }
        if (status == Constant.PULL_DOWN_FRESH) {
            mCurrentPage = 1;
        }
        DisposableSubscriber<ActionResult<List<CountryModel>>> consumer = new RxSubscriber<List<CountryModel>>() {
            @Override
            public void _onSuccess(List<CountryModel> countryModel) {
                //请求成功 更新ui
                mIView.getCountrySuccess(countryModel, status);

                mCurrentPage++;
                mIView.refreshOrDisLayout(status);

                //分页处理根据接口 来 如果可以判断最后一页 直接调用
//                mIView.loadNorMoreData();
            }

            @Override
            public void _onError(String error) {
                //请求失败
                mIView.refreshOrDisLayout(status);
                Log.d("aaa", "_onError" + error);
            }
        };
        MainReq.getInstance().getCountry(consumer, ip);
        addSubscribe(consumer);//添加订阅者，内部实现在页面关闭的时候取消订阅防止内存泄漏
    }
}
