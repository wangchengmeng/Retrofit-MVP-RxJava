package com.example.sunddenfix.retrofit.business.api;

import com.example.sunddenfix.retrofit.config.ActionResult;
import com.example.sunddenfix.retrofit.config.ConfigServer;
import com.example.sunddenfix.retrofit.bean.CountryModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author wangchengmeng
 */
public interface IMainService {

    //使用Flowable 返回列表的案例
    @GET(ConfigServer.API_ACTIVE_DEVICE)
    Flowable<ActionResult<List<CountryModel>>> getCountry(@Query("ip") String ip);

//   @GET("getIpInfo.php") 返回对象的案例
//   Flowable<ActionResult<CountryModel>> getCountry(@Query("ip") String ip);

}
