package com.example.sunddenfix.retrofit.config


/**
 *    @author : wangchengm
 *    @date   : 2020-05-1910:55
 *    @desc   : 常量管理类
 */
class Constant {
    companion object {

        //全局保存配置的文件名
        const val GLOBAL_FLAG_FILE_NAME = "GLOBAL_FLAG_FILE_NAME"

        //保存关闭新人红包弹窗的状态 的key
        const val FLAG_CLOSE_RED_PACKAGE = "FLAG_CLOSE_RED_PACKAGE"
        const val FLAG_DEVICE = "FLAG_DEVICE"
        const val FLAG_ACTIVE_DEVICE = "FLAG_ACTIVE_DEVICE"
        const val GIF_END_WITH = ".gif"


        //下拉刷新
        const val PULL_DOWN_FRESH = 0

        //上拉加载
        const val PULL_UP_LOAD = 1
    }
}