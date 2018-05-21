package com.example.sunddenfix.retrofit.base

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : IView>(protected val mActivity: Activity, protected val mView: T) : IPrensenter {

    private var mComposeDisable: CompositeDisposable? = null

    protected fun addSubscriber(disposable: Disposable) {
        if (null == mComposeDisable) {
            mComposeDisable = CompositeDisposable()
        }
        mComposeDisable?.add(disposable)
    }

    private fun unSubscriber() {
        if (null != mComposeDisable) {
            mComposeDisable?.dispose()
        }
    }

    override fun detatchView() {
        unSubscriber()
    }
}