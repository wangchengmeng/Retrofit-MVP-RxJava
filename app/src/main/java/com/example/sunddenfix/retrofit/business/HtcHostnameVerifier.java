package com.example.sunddenfix.retrofit.business;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * https域名验证器
 */
public class HtcHostnameVerifier implements HostnameVerifier {

    private String[] mHostUrls;

    public HtcHostnameVerifier(String[] hostUrls) {
        mHostUrls = hostUrls;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        boolean ret = false;
        for (String host : mHostUrls) {
            if (host.equalsIgnoreCase(hostname)) {
                ret = true;
            }
        }
        return ret;
    }
}