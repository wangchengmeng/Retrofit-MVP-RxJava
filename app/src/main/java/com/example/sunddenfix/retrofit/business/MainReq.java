package com.example.sunddenfix.retrofit.business;

import com.example.sunddenfix.retrofit.business.api.IMainService;
import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.utils.request.BaseApi;
import com.example.sunddenfix.retrofit.utils.rx.SchedulersHelper;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
      Retrofit retrofit = BaseApi.getRetrofit();
      mMainService = retrofit.create(IMainService.class);
   }

   /**
    * @param consumer 处理结果的 订阅者
    * @param ip       请求参数
    *                 return 返回Disposable 方便在销毁的时候取消订阅
    */
   public Disposable getCountry(Consumer<ActionResult<CountryModel>> consumer, String ip) {
      return mMainService.getCountry(ip)
                     .compose(SchedulersHelper.<ActionResult<CountryModel>>io2MainFlowable())
                     .subscribe(consumer);
   }

   /**
    * @param consumer 处理结果的 订阅者
    * @param ip       请求参数
    *                 return 返回Disposable 方便在销毁的时候取消订阅
    */
   public Disposable getCountry2(Consumer<ActionResult<CountryModel>> consumer, String ip) {
      return mMainService.getCountry2(ip)
                     .compose(SchedulersHelper.<ActionResult<CountryModel>>io2MainObservable())
                     .subscribe(consumer);
   }
}
