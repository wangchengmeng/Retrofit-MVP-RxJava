# Retrofit-MVP-RxJava
Retrofit+MVP+RxJava三者结合的框架搭建





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
        Observable.range(2, 8)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        //integer 2，3，4，5，6，7，8，9
                    }
                });

        //TODO 以上是 Rx1.0的版本的用法, 下面介绍一下 Rx2.0的用法。主要增加了背压策略，更改了部分api

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {

            }
        }, BackpressureStrategy.ERROR)//增加了 这个参数
                // 这种方式会在产生Backpressure问题的时候直接抛出一个异常,这个异常就是著名的MissingBackpressureException。
                .subscribe(new org.reactivestreams.Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //申请处理事件的个数
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        //和以往的一样 处理事件
                    }

                    @Override
                    public void onError(Throwable t) {
                        //处理错误
                    }

                    @Override
                    public void onComplete() {
                        //事件处理完毕后调用
                    }
                });

        //在Rx2.0之后没有之前的Action了，换成了Consumer

        //fromIterable 发送一个实现了Iterable的集合
        List<String> item = new ArrayList<>();
        item.add("Rx-1");
        Flowable.fromIterable(item)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //接受事件
                    }
                });

        //以上就是创建Observable的一些方法，大家可以自行测试一下
