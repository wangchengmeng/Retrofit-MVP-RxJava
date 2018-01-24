# Retrofit-MVP-RxJava
Retrofit+MVP+RxJava三者结合的框架搭建

       //一个常规的使用
      Consumer<ActionResult<CountryModel>> consumer = new RxConsumer<CountryModel>() {
         @Override
         public void _onSuccess(CountryModel countryModel) {
            mIView.updateText(countryModel);
         }

         @Override
         public void _onError(String error) {
            Log.d("aaa", "_onError" + error);
         }
      };

      Disposable disposable = MainReq.getInstance().getCountry(consumer, ip);
      addSubscrebe(disposable);//添加订阅者，内部实现在页面关闭的时候取消订阅防止内存泄漏

       //介绍RxConsumer 该类继承Consumer，并实现了accept方法

       所有返回的请求都会在这个方法这里处理，
       重点在_onSuccess()这个方法，留个开发者自行处理返回成功的情况，_onError处理错误的情况，至于其他情况
       比如token失效，单点登陆等，这些都可以在这里对所有的接口做统一处理


    @Override
     public void accept(@NonNull ActionResult<T> tActionResult) throws Exception {
      //在这里可以根据返回的不同code 做不同的事情 类似403这类型的错误code便可以统一处理
      Log.d(TAG, tActionResult.toString());
      switch (tActionResult.getCode()) {
         case ActionResult.RESULT_CODE_NO_LOGIN:
            //返回403 token失效
            break;
         case ActionResult.RESULT_CODE_NO_FOUND:
            //返回404  页面未找到
            break;
         case ActionResult.RESULT_CODE_SUCCESS:
            //返回200 请求成功
            _onSuccess(tActionResult.getData());
            break;
         default:
            if (!StringUtil.isNullOrEmpty(tActionResult.getMessage())) {
               //如果有返回的message
               _onError(tActionResult.getMessage());
            } else {
               _onError("网络链接出错");
            }
        }
     }



     public Disposable getCountry(Consumer<ActionResult<CountryModel>> consumer, String ip) {
        return mMainService.getCountry(ip)
                     .compose(SchedulersHelper.<ActionResult<CountryModel>>io2MainFlowable())
                     .subscribe(consumer);
    }

    订阅完成后会返回Disposable 方便对于已经处理完成的Observable进行取消，

    在BasePresenter中
    //取消所有的订阅
    protected void unSubscribe() {
      if (mCompositeDisposable != null) {
         mCompositeDisposable.dispose();
      }
    }

    //将创建的subscriber添加到这个集合中，方便在Activity销毁的时候取消订阅
    protected void addSubscrebe(Disposable disposable) {
      if (mCompositeDisposable == null) {
         mCompositeDisposable = new CompositeDisposable();
      }
        mCompositeDisposable.add(disposable);
     }

    //该方法直接在onDestroy的时候调用 避免内存泄漏
    @Override
    public void detatchView() {
      this.mIView = null;
      unSubscribe();
     }


    //线程切换的helpter


   //使用2.0ObservableTransformer 实现  当被观察者 是Observable的时候使用
    public static <T> ObservableTransformer<T, T> io2MainObservable() {
      return new ObservableTransformer<T, T>() {
         @Override
         public ObservableSource<T> apply(Observable<T> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                           .unsubscribeOn(AndroidSchedulers.mainThread())
                           .observeOn(AndroidSchedulers.mainThread());
         }
      };
   }

    //当被观察者 是FLowable的时候使用
     public static <T> FlowableTransformer<T, T> io2MainFlowable() {
      return new FlowableTransformer<T, T>() {
         @Override
         public Publisher<T> apply(Flowable<T> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                           .unsubscribeOn(AndroidSchedulers.mainThread())
                           .observeOn(AndroidSchedulers.mainThread());
         }
      };
    }











