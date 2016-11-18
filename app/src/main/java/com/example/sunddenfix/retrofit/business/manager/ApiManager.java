package com.example.sunddenfix.retrofit.business.manager;

import com.example.sunddenfix.retrofit.api.IApiService;
import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.utils.request.RequestUtils;

import rx.Observable;

/**
 * @author wangchengmeng
 * @desc 业务逻辑管理类
 */

public class ApiManager {

    //service
    private static IApiService manager = RequestUtils.getInstance().create(IApiService.class);

    public static Observable<ActionResult<CountryModel>> getCountry(String ip) {
        return manager.getCountry(ip);
    }
}
