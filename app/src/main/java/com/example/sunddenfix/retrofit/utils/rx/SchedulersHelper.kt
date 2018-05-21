package com.example.sunddenfix.retrofit.utils.rx

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wangchengm
 * on 2018/5/21.
 * Rx2.0实现线程切换封装的工具
 */

class SchedulersHelper {
    companion object {
        fun <T> ioMainObservable(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        fun <T> ioMainFlowable(): FlowableTransformer<T, T> {
            return FlowableTransformer {
                it.subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}