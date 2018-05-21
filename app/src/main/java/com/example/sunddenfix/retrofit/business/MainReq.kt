package com.example.sunddenfix.retrofit.business

import com.example.sunddenfix.retrofit.business.api.IMainService
import com.example.sunddenfix.retrofit.model.ActionResult
import com.example.sunddenfix.retrofit.model.CountryModel
import com.example.sunddenfix.retrofit.utils.request.BaseApi
import com.example.sunddenfix.retrofit.utils.rx.SchedulersHelper
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

class MainReq {

    private constructor() {
        mainService = BaseApi.getInstance().create(IMainService::class.java)
    }

    companion object {
        var INSTANCE: MainReq? = null
        lateinit var mainService: IMainService

        fun getInstance(): MainReq {
            if (null == INSTANCE) {
                INSTANCE = MainReq()
            }
            return INSTANCE!!
        }
    }

    /**
     * @param consumer 处理结果的 订阅者
     * @param ip       请求参数
     *                 return 返回Disposable 方便在销毁的时候取消订阅
     */
    fun getCountry(consumer: Consumer<ActionResult<CountryModel>>, ip: String): Disposable {
        return mainService.getCountry(ip)
                .compose(SchedulersHelper.ioMainFlowable())
                .subscribe(consumer)
    }

    /**
     * @param consumer 处理结果的 订阅者
     * @param ip       请求参数
     *                 return 返回Disposable 方便在销毁的时候取消订阅
     */
    fun getCountry2(consumer: Consumer<ActionResult<String>>, ip: String): Disposable {
        return mainService.getCountry2(ip)
                .compose(SchedulersHelper.ioMainObservable())
                .subscribe(consumer)
    }
}