package com.example.sunddenfix.retrofit.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * @author : wangchengm
 * @date : 2020-05-2215:20
 * @desc :
 */
object TimeUtils {

    /***
     * 检查网络
     * @return
     */
    fun checkNet(context: Context): Boolean {
        try {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                // 获取网络连接管理的对像
                val info = connectivity.activeNetworkInfo
                return !(info == null || !info.isAvailable)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}
