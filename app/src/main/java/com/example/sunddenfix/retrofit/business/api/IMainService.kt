package com.example.sunddenfix.retrofit.business.api

import com.example.sunddenfix.retrofit.model.ActionResult
import com.example.sunddenfix.retrofit.model.CountryModel
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author wangchengmeng
 */
interface IMainService {

    //使用Flowable
    @GET("getIpInfo.php")
    fun getCountry(@Query("ip") ip: String): Flowable<ActionResult<CountryModel>>

    //使用Observable
    @GET("getIpInfo.php")
    fun getCountry2(@Query("ip") ip: String): Observable<ActionResult<String>>

}
