package com.example.sunddenfix.retrofit.utils.request;

import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.requestIm.ManagerService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author wangchengmeng
 */
public class RequestUtils {
    private static OkHttpClient okHttpClient = new HttpClient().getOkHttpClient();

    private static final String URL_2 = "http://ip.taobao.com/service/";

    //初始化Retrofit
    public static final Retrofit mRetrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URL_2)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    //service

    private static final ManagerService manager = mRetrofit.create(ManagerService.class);


    public static Observable<ActionResult<CountryModel>> getCountry(String ip) {
        return manager.getCountry(ip);
    }
}
