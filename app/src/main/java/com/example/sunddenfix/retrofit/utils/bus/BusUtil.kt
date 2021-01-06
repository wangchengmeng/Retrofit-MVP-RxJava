package com.example.sunddenfix.retrofit.utils.bus


import org.greenrobot.eventbus.EventBus


/**
 * @author wangchengm
 * @desc EventBus的封装类
 */
object BusUtil {

    private const val TAG = "BusUtil"

    fun register(any: Any) {
        if (!EventBus.getDefault().isRegistered(any)) {
            EventBus.getDefault().register(any)
        }
    }

    fun unregister(any: Any) {
        if (EventBus.getDefault().isRegistered(any)) {
            EventBus.getDefault().unregister(any)
        }
    }

    fun post(model: EventBusModel?) {
        if (null == model) {
            return
        }
        EventBus.getDefault().post(model)
    }
}
