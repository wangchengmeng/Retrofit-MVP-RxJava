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

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

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
