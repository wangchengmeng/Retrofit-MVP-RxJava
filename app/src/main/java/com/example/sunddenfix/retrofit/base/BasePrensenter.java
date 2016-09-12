package com.example.sunddenfix.retrofit.base;

import android.app.Activity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author wangchengmeng
 */
public class BasePrensenter<T extends IView> implements IPrensenter {

    protected T mIView;
    protected Activity mActivity;

    protected CompositeSubscription mCompositeSubscription;

    public BasePrensenter(Activity activity,T view) {
        this.mActivity = activity;
        this.mIView = view;
    }

    //取消所有的订阅
    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
    //将创建的subscriber添加到这个集合中，方便在Activity销毁的时候取消订阅
    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void detatchView() {
        this.mIView = null;
        unSubscribe();
    }
}
