package com.example.sunddenfix.retrofit.utils.bus

import java.io.Serializable

/**
 * EventBus传递数据的model
 *
 * @author wangchengm
 */
data class EventBusModel(val eventBusAction: String, val eventBusObject: Any?) : Serializable