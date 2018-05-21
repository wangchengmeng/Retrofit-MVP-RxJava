package com.example.sunddenfix.retrofit.utils.rx

import android.util.Log
import com.example.sunddenfix.retrofit.model.ActionResult
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription


/**
 * @author wangchengmeng
 * @desc Subscriber的重新封装
 * @更新时间 2018-5-21
 */
class RxSubscriber<T> : FlowableSubscriber<ActionResult<T>> {
    override fun onComplete() {

    }

    override fun onError(t: Throwable?) {
        Log.d(RxSubscriber::class.java.simpleName, t.toString())
        onErrors("网络错误")
    }

    override fun onSubscribe(s: Subscription) {
        s.request(Long.MAX_VALUE)
    }

    override fun onNext(t: ActionResult<T>?) {
        when (t?.code) {
            ActionResult.RESULT_CODE_NO_LOGIN -> {
                //403 toekn失效
            }
            ActionResult.RESULT_CODE_NO_FOUND -> {
                //404 页面找不到
            }
            ActionResult.RESULT_CODE_SUCCESS -> {
                //200 成功
                onSucess(t.data)
            }
            else -> {
                if (t?.message.isNullOrEmpty()) {
                    onErrors("网络错误")
                } else {
                    onErrors(t?.message)
                }
            }
        }
    }

    fun onSucess(data: T?) {

    }

    fun onErrors(error: String?) {

    }
}