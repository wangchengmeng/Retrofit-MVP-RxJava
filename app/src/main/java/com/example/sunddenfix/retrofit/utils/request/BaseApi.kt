package com.example.sunddenfix.retrofit.utils.request

import android.util.Log
import com.example.sunddenfix.retrofit.App
import com.example.sunddenfix.retrofit.R
import com.example.sunddenfix.retrofit.business.HtcHostnameVerifier
import com.example.sunddenfix.retrofit.utils.IOUtil
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.MalformedURLException
import java.net.URL
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

class BaseApi private constructor() {
    companion object {
        private const val TAG: String = "okHttpClient"
        private const val URL_2: String = "http://ip.taobao.com/service/"
        private const val DEFAULT_TIMEOUT: Long = 10//链接超时时间
        private var mRetrofit: Retrofit? = null

        fun getInstance() = getRetrofit(URL_2)

        private fun getRetrofit(url: String): Retrofit {
            if (null == mRetrofit || url != mRetrofit?.baseUrl().toString()) {
                val builder: OkHttpClient.Builder = OkHttpClient.Builder().readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)
                        .addInterceptor(loggingInterceptor)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

                if (url.toLowerCase().contains("https")) {
                    try {
                        builder.sslSocketFactory(getSSLSocketFactory(intArrayOf(R.raw.tunhuoji)))//证书
                        builder.hostnameVerifier(HtcHostnameVerifier(arrayOf(getApiHost())))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG, e.message)
                    }
                }

                val okHttpClient = builder.build()
                mRetrofit = Retrofit.Builder().client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())//添加gson
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rx
                        .baseUrl(url)
                        .build()
            }
            return mRetrofit!!

        }

        @Throws(Exception::class)
        private fun getSSLSocketFactory(certificates: IntArray): SSLSocketFactory {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)

            for (i in certificates.indices) {
                val certificate = App.getInstance()!!
                        .resources
                        .openRawResource(certificates[i])
                keyStore.setCertificateEntry(i.toString(), certificateFactory.generateCertificate(certificate))
                IOUtil.closeStream(certificate)
            }
            val sslContext = SSLContext.getInstance("TLS")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            return sslContext.socketFactory
        }

        /**
         * 获取url的Host
         *
         * @return
         */
        private fun getApiHost(): String {
            val url: URL
            try {
                url = URL(getApiUrl())
                return url.host
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return ""
        }

        /**
         * 获取接口地址
         *
         * @return String API的基本地址
         */
        private fun getApiUrl(): String {
            return URL_2
        }

        private val interceptor: Interceptor = Interceptor {
            val request = it.request()

            val newRequest = request.newBuilder()
                    .addHeader("AC-X-TYPE", "1")
                    .addHeader("Accept", "application/json")
                    .build()
            it.proceed(newRequest)
        }

        private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d(TAG, it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}