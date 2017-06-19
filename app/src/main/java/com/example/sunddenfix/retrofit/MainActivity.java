package com.example.sunddenfix.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunddenfix.retrofit.base.BaseActivity;
import com.example.sunddenfix.retrofit.presenter.MainPresenter;
import com.example.sunddenfix.retrofit.model.CountryModel;
import com.example.sunddenfix.retrofit.utils.rx.RxUtil;
import com.example.sunddenfix.retrofit.viewIm.MainView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

   private TextView mTextView;
   private Button mBtnClick;
   private TextView mTvResult;
   private int looper = 0;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      initViews();
      getMainData();
   }

   private void initViews() {
      mTextView = (TextView) findViewById(R.id.tv_name);
      mTvResult = (TextView) findViewById(R.id.tv_result);
      mBtnClick = (Button) findViewById(R.id.btn_click);
   }

   private void getMainData() {
//      mIPresenter.getCountry("21.22.11.33");
      mIPresenter.getCountry2("21.22.11.33");
   }

   @Override
   protected MainPresenter getPresenter() {
      return new MainPresenter(this, this);
   }

   @Override
   public void updateText(CountryModel model) {
      mTextView.setText(model.getCountry() + ":" + model.getIp());
   }














   /**
    * 下面是关于rx的一些操作符的使用
    */

   private void partArray() {
      //遍历数组或者list集合
      String[] arrs = {"a", "b", "c", "d", "e"};
      final StringBuilder builder = new StringBuilder();
      Observable.from(arrs)
              .subscribe(new Action1<String>() {
                 @Override
                 public void call(String s) {
                    builder.append(s + ":");
                    mTvResult.setText(builder.toString());
                 }
              }).unsubscribe();
   }

   private void testLoopOperate() {
      //每隔两秒执行一次
      Observable.interval(2, TimeUnit.SECONDS)
              .compose(RxUtil.<Long>ioMain())
              .subscribe(new Action1<Long>() {
                 @Override
                 public void call(Long aLong) {
                    Log.d("aaa", "along:" + aLong);
                    mTvResult.setText("along:" + aLong);
                 }
              });
   }

   private void testLimitClick() {
      //限制频繁点击  限制5秒
      RxView.clicks(mBtnClick)
              .throttleFirst(5, TimeUnit.SECONDS)
              .subscribe(new Observer<Object>() {
                 @Override
                 public void onCompleted() {
                    Log.d("aaa", "onCompleted:");
                 }

                 @Override
                 public void onError(Throwable e) {
                    Log.d("aaa", "onError:");
                 }

                 @Override
                 public void onNext(Object o) {
                    Log.d("aaa", "onNext:");
                    Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                 }
              });
   }

   private void testPeriodically() {
      //使用schedulePeriodically做轮询请求
      Observable.create(new Observable.OnSubscribe<String>() {
         @Override
         public void call(final Subscriber<? super String> subscriber) {
            Schedulers.newThread().createWorker()
                    .schedulePeriodically(new Action0() {
                       @Override
                       public void call() {
                          subscriber.onNext("轮询请求==========");
                       }
                    }, 1, 4, TimeUnit.SECONDS); //1 首次延迟时间   4 每间隔时间
         }
      }).compose(RxUtil.<String>ioMain())
              .subscribe(new Action1<String>() {
                 @Override
                 public void call(String s) {
                    Log.d("aaa", s);
                    mTvResult.setText(s + looper++);
                 }
              });
   }

}
