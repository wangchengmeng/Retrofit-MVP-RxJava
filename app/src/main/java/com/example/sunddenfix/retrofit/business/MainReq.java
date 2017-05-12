package com.example.sunddenfix.retrofit.business;

import com.example.sunddenfix.retrofit.business.api.IMainService;
import com.example.sunddenfix.retrofit.model.ActionResult;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.utils.request.BaseApi;
import com.example.sunddenfix.retrofit.utils.rx.RxUtil;

import retrofit2.Retrofit;
import rx.Subscriber;

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
    * @param subscriber 处理结果的 订阅者
    * @param ip         请求参数
    */
   public  void getCountry(Subscriber<ActionResult<CountryModel>> subscriber, String ip) {
      mMainService.getCountry(ip)
              .compose(RxUtil.<ActionResult<CountryModel>>ioMain()) //这里封装的切换线程 只需调用一下即可达到异步
              .subscribe(subscriber); //订阅
   }
}
