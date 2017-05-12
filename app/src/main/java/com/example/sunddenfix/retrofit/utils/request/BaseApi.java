package com.example.sunddenfix.retrofit.utils.request;

import android.util.Log;

import com.example.sunddenfix.retrofit.App;
import com.example.sunddenfix.retrofit.R;
import com.example.sunddenfix.retrofit.business.HtcHostnameVerifier;
import com.example.sunddenfix.retrofit.utils.IOUtil;
import com.example.sunddenfix.retrofit.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangchengmeng
 */
public class BaseApi {

   private static final String TAG = "okHttpClient";

   private static final String URL_2 = "http://ip.taobao.com/service/";

   private static final int DEFAULT_TIMEOUT = 10;
   private static Retrofit mRetrofit;

   public static Retrofit getRetrofit() {
      return getRetrofit(URL_2);
   }

   private static Retrofit getRetrofit(String url) {
      if (null == mRetrofit || !url.equals(mRetrofit.baseUrl().toString())) {
         OkHttpClient.Builder builder = new OkHttpClient.Builder()
                                                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                                .addInterceptor(interceptor)//拦截器处理通用Header
                                                .addInterceptor(loggingInterceptor) //日志处理
                                                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

         if (!StringUtil.isNullOrEmpty(url) && url.toLowerCase().contains("https:")) {
            try {
               builder.sslSocketFactory(getSSLSocketFactory(new int[]{R.raw.tunhuoji}));//证书
               builder.hostnameVerifier(new HtcHostnameVerifier(new String[]{getApiHost()}));
            } catch (Exception e) {
               e.printStackTrace();
               Log.e(TAG, e.getMessage());
            }
         }

         OkHttpClient client = builder.build();

         mRetrofit = new Retrofit.Builder()
                             .client(client)
                             .addConverterFactory(GsonConverterFactory.create())//添加gson
                             .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加rx
                             .baseUrl(url)
                             .build();
      }
      return mRetrofit;
   }

   private static SSLSocketFactory getSSLSocketFactory(int[] certificates) throws Exception {
      CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      keyStore.load(null, null);

      for (int i = 0; i < certificates.length; i++) {
         InputStream certificate = App.getInstance().getResources().openRawResource(certificates[i]);
         keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));
         IOUtil.closeStream(certificate);
      }
      SSLContext sslContext = SSLContext.getInstance("TLS");
      TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      trustManagerFactory.init(keyStore);
      sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
      return sslContext.getSocketFactory();
   }

   /**
    * 添加头部信息
    */
   private static Interceptor interceptor = new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
         Request request = chain.request();

         Request newRequest = request.newBuilder()
                                      .addHeader("AC-X-TYPE", "1")
                                      .addHeader("Accept", "application/json")
                                      .build();

         return chain.proceed(newRequest);
      }
   };

   /**
    * 网络请求打印日志
    */
   private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
      @Override
      public void log(String message) {
         Log.d(TAG, message);
      }
   }).setLevel(HttpLoggingInterceptor.Level.BODY);


   /**
    * 获取url的Host
    *
    * @return
    */
   private static String getApiHost() {
      URL url;
      try {
         url = new URL(getApiUrl());
         return url.getHost();
      } catch (MalformedURLException e) {
         e.printStackTrace();
      }
      return "";
   }

   /**
    * 获取接口地址
    *
    * @return String API的基本地址
    */
   private static String getApiUrl() {
      return URL_2;
   }
}
