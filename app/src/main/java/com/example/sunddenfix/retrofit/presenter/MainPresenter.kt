package com.example.sunddenfix.retrofit.presenter

import android.app.Activity
import android.util.Log
import com.example.sunddenfix.retrofit.base.BasePresenter
import com.example.sunddenfix.retrofit.business.MainReq
import com.example.sunddenfix.retrofit.model.CountryModel
import com.example.sunddenfix.retrofit.utils.rx.RxConsumer
import com.example.sunddenfix.retrofit.viewIm.MainView

class MainPresenter(activity: Activity, view: MainView) : BasePresenter<MainView>(activity, view) {

    /**
     * 一般返回数据格式：
     * code：200
     * data：{} 或者[]
     * message：返回信息
     * 匿名对象 object:
     */
    fun getCountry(ip: String) {
        val consumer = object : RxConsumer<CountryModel>() {
            override fun onSuccess(result: CountryModel?) {
                mView.updateText(result!!)
            }

            override fun onErrors(error: String?) {
                Log.d(MainPresenter::class.java.simpleName, error)
            }
        }

        val disposable = MainReq.getInstance().getCountry(consumer, ip)

        addSubscriber(disposable)//添加订阅者，内部实现在页面关闭的时候取消订阅防止内存泄漏
    }

    /**
     * 一般返回数据格式：
     * code：200
     * data：{} 或者[]
     * message：返回信息
     * 匿名对象 object:
     */
    fun getCountry2(ip: String) {
        val consumer = object : RxConsumer<String>() {
            override fun onSuccess(result: String?) {
                mView.updateText2(result!!)
            }

            override fun onErrors(error: String?) {
                Log.d(MainPresenter::class.java.simpleName, error)
            }
        }

        val disposable = MainReq.getInstance().getCountry2(consumer, ip)

        addSubscriber(disposable)//添加订阅者，内部实现在页面关闭的时候取消订阅防止内存泄漏
    }

}