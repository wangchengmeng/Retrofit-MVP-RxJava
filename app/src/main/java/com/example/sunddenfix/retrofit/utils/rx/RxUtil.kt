package com.example.sunddenfix.retrofit.utils.rx

import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @wangchengm
 * @date 2017-5-21
 * @desc 异步线程切换帮助类
 */
class RxUtil {

    companion object {
        fun <T> ioMain(): Observable.Transformer<T, T> {
            return Observable.Transformer {
                it.subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}