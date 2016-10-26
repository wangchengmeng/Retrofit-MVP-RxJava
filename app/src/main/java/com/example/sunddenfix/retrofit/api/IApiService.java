package com.example.sunddenfix.retrofit.api;

import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.model.CountryModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author wangchengmeng
 */
public interface IApiService {

    //URL后面参数  用Query，如果需要动态设置路径用Path(这里只是模拟动态路径传递)
//    @GET("movie/{top250}")
//    Observable<Response> getMainData(@Path("top250") String top250, @Query("start") int start, @Query("count") int count);

    @GET("getIpInfo.php")
    Observable<ActionResult<CountryModel>> getCountry(@Query("ip") String ip);
}
