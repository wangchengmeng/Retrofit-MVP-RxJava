package com.example.sunddenfix.retrofit.base;

import android.app.Activity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author wangchengmeng
 */
public class BasePrensenter<T extends IView> implements IPrensenter {

   protected T mIView;
   protected Activity mActivity;

   //2.0之后的 取消订阅
   protected CompositeDisposable mCompositeDisposable;

   public BasePrensenter(Activity activity, T view) {
      this.mActivity = activity;
      this.mIView = view;
   }

   //取消所有的订阅
   protected void unSubscribe() {
      if (mCompositeDisposable != null) {
         mCompositeDisposable.dispose();
      }
   }

   //将创建的subscriber添加到这个集合中，方便在Activity销毁的时候取消订阅
   protected void addSubscrebe(Disposable disposable) {
      if (mCompositeDisposable == null) {
         mCompositeDisposable = new CompositeDisposable();
      }
      mCompositeDisposable.add(disposable);
   }

   @Override
   public void detatchView() {
      this.mIView = null;
      unSubscribe();
   }
}
