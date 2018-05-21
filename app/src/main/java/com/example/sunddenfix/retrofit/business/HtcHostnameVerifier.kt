package com.example.sunddenfix.retrofit.business

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * https域名验证器
 */
class HtcHostnameVerifier(private val mHostUrls: Array<String>) : HostnameVerifier {

    override fun verify(hostname: String, session: SSLSession): Boolean {
        var ret = false
        for (host in mHostUrls) {
            if (host.equals(hostname, ignoreCase = true)) {
                ret = true
            }
        }
        return ret
    }
}