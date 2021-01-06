package com.example.sunddenfix.retrofit.utils.rx;

import android.text.TextUtils;
import android.util.Log;

import com.example.sunddenfix.retrofit.config.ActionResult;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author wangchengmeng
 * @desc Subscriber的重新封装
 * 网络请求的统一处理，具体实现交给需要实现的方法
 */
public class RxSubscriber<T> extends DisposableSubscriber<ActionResult<T>> {
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
                if (!TextUtils.isEmpty(tActionResult.getMessage())) {
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
