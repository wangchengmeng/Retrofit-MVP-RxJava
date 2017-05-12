package com.example.sunddenfix.retrofit.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.sunddenfix.retrofit.base.BasePrensenter;
import com.example.sunddenfix.retrofit.business.MainReq;
import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.utils.rx.RxSubscriber;
import com.example.sunddenfix.retrofit.viewIm.MainView;

import rx.Subscriber;

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
      Subscriber<ActionResult<CountryModel>> subscriber = new RxSubscriber<CountryModel>() {
         @Override
         public void _onSuccess(CountryModel model) {
            mIView.updateText(model);
         }

         @Override
         public void _onError(String error) {
            Log.d("aaa", "_onError" + error);
         }
      };

      MainReq.getInstance().getCountry(subscriber, ip);
      addSubscrebe(subscriber);//添加订阅者，内部实现在页面关闭的时候取消订阅防止内存泄漏
   }
}
