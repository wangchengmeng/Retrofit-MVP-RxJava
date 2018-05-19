package com.example.sunddenfix.retrofit.utils.rx;

import android.util.Log;

import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.utils.StringUtil;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;

/**
 * @author wangchengmeng
 * @desc Subscriber的重新封装
 * @更新时间 2015-9-9
 */
public class RxSubscriber<T> implements FlowableSubscriber<ActionResult<T>> {
   private static final String TAG = "RxSubscriber";

   @Override
   public void onError(Throwable e) {
      //该方法在出错的时候一定会调用
      Log.d(TAG, e.toString());
      _onError("网络链接出错");
   }

   @Override
   public void onComplete() {
      //订阅处理完成 自定义处理
   }


   @Override
   public void onSubscribe(@NonNull Subscription s) {
      s.request(Long.MAX_VALUE);//不限制请求数量 设置为 max
   }

   @Override
   public void onNext(ActionResult<T> tActionResult) {
      //在这里可以根据返回的不同code 做不同的事情 类似403这类型的错误code便可以统一处理
      Log.d(TAG, tActionResult.toString());
      switch (tActionResult.getCode()) {
         case ActionResult.RESULT_CODE_NO_LOGIN:
            //返回403 token失效
            break;
         case ActionResult.RESULT_CODE_NO_FOUND:
            //返回404  页面未找到
            break;
         case ActionResult.RESULT_CODE_SUCCESS:
            //返回200 请求成功
            _onSuccess(tActionResult.getData());
            break;
         default:
            if (!StringUtil.isNullOrEmpty(tActionResult.getMessage())) {
               //如果有返回的message
               _onError(tActionResult.getMessage());
            } else {
               _onError("网络链接出错");
            }
      }
   }

   //该方法在实例化的时候留给自己去实现
   public void _onSuccess(T t) {

   }

   //该方法在实例化的时候留给自己去实现
   public void _onError(String error) {

   }
}
