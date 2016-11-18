package com.example.sunddenfix.retrofit.utils.request;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangchengmeng
 */
public class RequestUtils {
    private static OkHttpClient okHttpClient = new HttpClient().getOkHttpClient();

    private static final String URL_2 = "http://ip.taobao.com/service/";

    private final static Object mLock = new Object();

    //初始化Retrofit
    private static Retrofit mRetrofit = null;


    //获取Retrofit对象 预防多线程
    public static Retrofit getInstance() {
        if (null == mRetrofit) {
            synchronized (mLock) {
                if (null == mRetrofit) {
                    mRetrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .baseUrl(URL_2)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return mRetrofit;
    }
}
