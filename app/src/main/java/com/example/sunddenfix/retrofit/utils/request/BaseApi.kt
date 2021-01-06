@file:JvmName("BaseApi")
package com.example.sunddenfix.retrofit.utils.request


import android.util.Log
import com.example.sunddenfix.retrofit.App
import com.example.sunddenfix.retrofit.config.ConfigServer
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.*
import okhttp3.CacheControl.FORCE_CACHE
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


public class BaseApi private constructor() {
    companion object {
        private const val TAG: String = "okHttpClient"
        private const val DEFAULT_TIMEOUT: Long = 15//链接超时时间
        private var mRetrofit: Retrofit? = null

        fun getInstance() = getRetrofit(ConfigServer.getAppBaseUrl(ConfigServer.API_INDEX))

        private fun getRetrofit(url: String): Retrofit {
            if (null == mRetrofit || url != mRetrofit?.baseUrl().toString()) {
                val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(cacheInterceptor)
                    .addNetworkInterceptor(cacheInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .cache(
                        Cache(
                            //网络数据缓存文件
                            File(App.getInstance().cacheDir, "net_data_cache"),
                            10 * 1024 * 1024
                        )
                    )
                    .connectionSpecs(
                        listOf(
                            ConnectionSpec.MODERN_TLS,
                            ConnectionSpec.COMPATIBLE_TLS
                        )
                    )

                val okHttpClient = builder.build()

                mRetrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())//添加gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rx
                    .client(okHttpClient)
                    .build()
            }
            return mRetrofit!!
        }

        /**
         * 网络请求头 拦截器
         */
        private val interceptor: Interceptor = Interceptor {
            val newRequest = it.request().newBuilder()
            val build = newRequest
                .addHeader("x-os", "Android")
                .build()
            it.proceed(build)
        }

        /**
         * 网络请求 日志打印拦截器
         */
        private val loggingInterceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d(TAG, it)
            }).setLevel(HttpLoggingInterceptor.Level.BODY)


        //网络数据缓存
        private var cacheInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (com.example.sunddenfix.retrofit.utils.TimeUtils.checkNet(App.getInstance())) {
                //有网时
                val response = chain.proceed(request)
                val cacheControl = request.cacheControl().toString()
                Log.e("yjbo-cache", "在线缓存在1分钟内可读取$cacheControl")
                val netGet = 0
                when (netGet) {
                    //总获取实时信息
                    0 -> return@Interceptor response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + 0)
                        .build()
                    //t（s）之后获取实时信息--此处是
                    1 -> return@Interceptor response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=${60}")
                        .build()
                }
                null
            } else { //无网时
                val nonetGet = 0
                when (nonetGet) {
                    //此处不设置过期时间
                    0 -> request = request.newBuilder()
                        .cacheControl(FORCE_CACHE)
                        .build()
                    //此处设置了t秒---修改了系统方法
                    1 -> request = request.newBuilder()
                        .cacheControl(FORCE_CACHE1)
                        .build()
                }
                val response = chain.proceed(request)
                //下面注释的部分设置也没有效果，因为在上面已经设置了
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached")
                    .removeHeader("Pragma")
                    .build()
            }
        }

        //这是设置在多长时间范围内获取缓存里面
        var FORCE_CACHE1 = CacheControl.Builder()
            .onlyIfCached()
            .maxStale(
                60,
                TimeUnit.SECONDS
            ) //CacheControl.FORCE_CACHE--是int型最大值
            .build()
    }

}