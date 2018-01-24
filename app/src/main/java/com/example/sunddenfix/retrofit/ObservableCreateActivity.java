package com.example.sunddenfix.retrofit;

import android.os.Bundle;
import android.util.Log;

import com.example.sunddenfix.retrofit.base.BaseActivity;
import com.example.sunddenfix.retrofit.presenter.MainPresenter;
import com.example.sunddenfix.retrofit.utils.rx.RxUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author wangchengm
 * @desc 介绍最基础的rx使用方法，从rx1.0使用过度到rx2.0的使用，
 * 适合刚入门的rx学者
 * <p>
 * 主要是由Observable（被观察者）发射出事件，然后subscribe（订阅）Observer（观察者）然后Observer会
 * 接收到事件，并进行处理的一个过程。
 * <p>
 * 为什么是被观察者订阅观察者呢？
 * 方便这种链式调用
 */
public class ObservableCreateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        //创建被观察者 Observable的几种方式

        //1. create
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //发射事件
                subscriber.onNext("Rxjava-1");
                subscriber.onNext("Rxjava-2");
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                //事件接受完成后 调用该方法
            }

            @Override
            public void onError(Throwable e) {
                //出错的时候调用该方法

                //注意 onCompleted 和 onError只要调用其中一个就结束
            }

            @Override
            public void onNext(String s) {
                //接受发射的 onNext方法
            }
        });

        //2.just 注意：最多只可以发送10个事件
        Observable.just(1, 2, 3, 4, 5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                //当需求只在乎 onNext方法的时候 只需要使用Action
            }
        });

        //3.from 接受一个list 或者 array
        String[] args = {"rx-1", "rx-2", "rx-3"};
        Observable.from(args)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });

        //4.该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
        Observable.empty();

        //5.该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
        Observable.error(new RuntimeException());

        //6.never() 该方法创建的被观察者对象发送事件的特点：不发送任何事件 似乎没啥作用
        Observable.never();

        //7.defer() 延迟创建Observable 在订阅的时候才会创建
//        Observable.defer(new Func0<Observable<? extends Object>>() {
//            @Override
//            public Observable<? extends Object> call() {
//                return null;
//            }
//        });

        //8.timer() 延迟3s，发送一个long类型数值

        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                    }
                });

        //9.interval() 延迟2s发送第一个事件后，每隔1s发送一个事件
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                    }
                });
        //10.range()
        // 参数1 : 事件序列起始点；
        // 参数2 : 事件数量；
        // 若设置为负数，则会抛出异常  从2开始发送，每次发送事件递增1，一共发送8个事件
        Observable.range(2,8)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        //integer 2，3，4，5，6，7，8，9
                    }
                });

        //以上是 Rx1.0的版本, Rx2.0增加了被压策略，更改了部分api


    }


    @Override
    protected MainPresenter getPresenter() {
        return null;
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
                    }
                });
    }

    private void testLimitClick() {
        //限制频繁点击  限制5秒
        //      RxView.clicks(mBtnClick)
        //              .throttleFirst(5, TimeUnit.SECONDS)
        //              .subscribe(new Observer<Object>() {
        //                 @Override
        //                 public void onCompleted() {
        //                    Log.d("aaa", "onCompleted:");
        //                 }
        //
        //                 @Override
        //                 public void onError(Throwable e) {
        //                    Log.d("aaa", "onError:");
        //                 }
        //
        //                 @Override
        //                 public void onNext(Object o) {
        //                    Log.d("aaa", "onNext:");
        //                    Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
        //                 }
        //              });
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
                    }
                });
    }

}
