package com.example.sunddenfix.retrofit.utils.rx;

import android.util.Log;

import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.utils.StringUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by wangchengm
 * on 2017/6/19.
 * 对consumer对封装
 */

public class RxConsumer<T> implements Consumer<ActionResult<T>> {

   private static final String TAG = "RxConsumer";

   @Override
   public void accept(@NonNull ActionResult<T> tActionResult) throws Exception {
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
