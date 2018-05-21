package com.example.sunddenfix.retrofit.model

/**
 * @author wangchengmeng
 */
class ActionResult<T> {

    companion object {
        /**
         * 接口正常
         */
        const val RESULT_CODE_SUCCESS = "0"
        /**
         * 网络异常
         */
        const val RESULT_CODE_NET_ERROR = "111"
        /**
         * 未授权
         */
        const val RESULT_CODE_NO_AUTH = "401"
        /**
         * token过期状态
         */
        const val RESULT_CODE_NO_LOGIN = "403"
        /**
         * 页面未发现
         */
        const val RESULT_CODE_NO_FOUND = "404"

    }

    /**
     * 一般返回数据格式：
     * code：200
     * data：{} 或者[]
     * message：返回信息
     */
    var code: String? = null
    var message: String? = null
    var data: T? = null


    override fun toString(): String {
        return "ActionResult{" +
                "code='" + code + '\''.toString() +
                ", message='" + message + '\''.toString() +
                ", Object=" + data +
                '}'.toString()
    }
}