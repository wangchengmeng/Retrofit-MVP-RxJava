package com.example.sunddenfix.retrofit.utils.rx

import com.example.sunddenfix.retrofit.model.ActionResult
import io.reactivex.functions.Consumer


/**
 * Created by wangchengm
 * on 2018/5/21.
 * 对consumer对封装
 * 对于 Subscriber 只需要onNext方法的时候 就可以使用Consumer
 */

open class RxConsumer<T> : Consumer<ActionResult<T>> {

    override fun accept(t: ActionResult<T>?) {
        when (t?.code) {
            ActionResult.RESULT_CODE_NO_LOGIN -> {
                //403 token失效
            }
            ActionResult.RESULT_CODE_NO_FOUND -> {
                //404页面没找到
            }
            ActionResult.RESULT_CODE_SUCCESS -> {
                //200 成功
                onSuccess(t.data)
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

    open fun onSuccess(result: T?) {

    }

    open fun onErrors(error: String?) {

    }

}