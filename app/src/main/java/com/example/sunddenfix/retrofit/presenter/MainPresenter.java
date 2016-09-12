package com.example.sunddenfix.retrofit.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.sunddenfix.retrofit.base.BasePrensenter;
import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.utils.request.RequestUtils;
import com.example.sunddenfix.retrofit.utils.rx.RxSubscriber;
import com.example.sunddenfix.retrofit.utils.rx.RxUtil;
import com.example.sunddenfix.retrofit.viewIm.MainView;

/**
 * @author wangchengmeng
 */
public class MainPresenter extends BasePrensenter<MainView> {

    public MainPresenter(Activity activity, MainView view) {
        super(activity, view);
    }

    /**
     * 一般返回数据格式：
     * code：200
     * data：{} 或者[]
     * message：返回信息
     */
    public void getCountry(String ip) {
        RxSubscriber subscriber = new RxSubscriber<CountryModel>() {
            @Override
            public void _onSuccess(CountryModel model) {
                Log.d("aaa", "_onSuccess" + model.toString());
                mIView.updateText(model);
            }

            @Override
            public void _onError(String error) {
                Log.d("aaa", "_onError" + error);
            }
        };

        RequestUtils.getCountry(ip)
                .compose(RxUtil.<ActionResult<CountryModel>>ioMain())
                .subscribe(subscriber);
        addSubscrebe(subscriber);
    }
}
