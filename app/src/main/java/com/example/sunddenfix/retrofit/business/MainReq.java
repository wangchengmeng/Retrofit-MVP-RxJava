package com.example.sunddenfix.retrofit.business;

import com.example.sunddenfix.retrofit.business.api.IMainService;
import com.example.sunddenfix.retrofit.config.ActionResult;
import com.example.sunddenfix.retrofit.bean.CountryModel;
import com.example.sunddenfix.retrofit.utils.request.BaseApi;
import com.example.sunddenfix.retrofit.utils.rx.SchedulersHelper;

import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Retrofit;

/**
 * @author wangchengmeng
 * @desc 首页业务逻辑管理类
 */

public class MainReq {

    private static MainReq INSTANCE;
    private IMainService mMainService;

    public static MainReq getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new MainReq();
        }
        return INSTANCE;
    }

    /**
     * 在实例化请求类的时候 就获取到IMainService对象
     */
    private MainReq() {
        Retrofit retrofit = BaseApi.Companion.getInstance();
        mMainService = retrofit.create(IMainService.class);
    }

    /**
     * @param consumer 处理结果的 订阅者
     * @param ip       请求参数
     *                 return 返回Disposable 方便在销毁的时候取消订阅
     */
    public void getCountry(DisposableSubscriber<ActionResult<List<CountryModel>>> consumer, String ip) {
        mMainService.getCountry(ip)
                .compose(SchedulersHelper.io2MainFlowable())
                .subscribe(consumer);
    }
}
