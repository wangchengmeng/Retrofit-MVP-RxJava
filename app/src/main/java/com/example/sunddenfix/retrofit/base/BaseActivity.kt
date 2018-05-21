package com.example.sunddenfix.retrofit.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<T : IPrensenter> : AppCompatActivity() {

    protected lateinit var mIPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIPresenter = getPresenter()
        if (0 != getResourceId()) {
            setContentView(getResourceId())
        }
    }

    protected abstract fun getPresenter(): T

    protected abstract fun getResourceId(): Int

    override fun onDestroy() {
        super.onDestroy()
        mIPresenter.detatchView()
    }
}