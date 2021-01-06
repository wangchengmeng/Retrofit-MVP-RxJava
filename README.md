项目说明：
上线说明：
1：注意切换环境 测试 予发布 线上 ConfigServer  API_INDEX（ 0 测试 1予发布 2 线上）
2：记得修改版办号（gradle.properties 文件中）
3：AndroidManifest.xml  关闭log开关 is_debug：（release-false  debug-true）

整个项目使用java 语言编写
1：整体架构设计
    MVP+Rx+Retrofit 这是一套完整的架构设计，用MVP这种设计模式，结合Rx这种响应式的方式降低耦合度，网络框架使用了okhttp的封装框架Retrofit
    该框架也支持Rx这种响应式。

2.使用到的三方库：
    目前图片加载框架使用的是Glide(自己包装了一层，在util包下的glide包下，方便后期如果更换，只需要在包装类中做修改)。通知还是使用的EventBus。
    网络请求框架Retrofit，异步处理Rxjava RxAndroid。线程切换工具类SchedulersHelper。

    例外：DisposableSubscriber对这个类进行了包装，方便以后所有的请求处理都在RxSubscriber中的onNext方法处理，比如后期需要增加不同的状态做不同的事情，只需要在这里增加即可
    不需要再所有请求的地方去添加。针对请求结果处理，自己实现_onSuccess方法，结合泛型，该方法拿到的就是自己需要的数据模型。

3.项目包说明：
    base：
        该包下存放的都是基类，以及顶层的父类以及接口。命名规则：基类以Base开头，顶层接口以I开头
    bean：
        该包下存放的是所有的数据model，命名规则：功能名称+bean  比如UserBean 用户的数据model
    config：
        该包下存放的都是常量管理类，ActionResult 接口返回的统一标准；configServer接口相关的，比如接口名称，域名，等常量管理类；
        Constant 其他常量。
    exception：
        自定义的异常处理类
    listener：
        自定义的Listener类
    presenter：
        presenter管理类，所有的逻辑处理presenter都在包下统一管理， 命名规则：功能+pre(名字太长的可缩写成pre) 比如：LoginPre
    req：
        请求管理类。
            该包下的api包：所有页面的接口请求定义的Service，命名规则：I+功能+service 比如：ILoginService 登陆相关的请求定义接口
            该包下的http：关于http相关的工具 和 封装的Retrofit管理类。后面如需定义接口返回的statusCode 也放在这里
        所有的请求处理类，命名规则：功能+Req，表示某个页面的请求处理类。比如首页： MainReq  登陆 LoginReq

    ui：
    //分包 待商榷，有2种方式，1：四大组件方式，2功能模块方式，商榷后确定即可
        所有界面相关的类。没有完全的按照功能分包，因为像presenter 以及 req 这些没必要每个功能包下都建一个，可以放在一个包统一进行管理，出于这个考虑所以没有完全按照功能分，可以说是按照
        逻辑处理，以及界面显示将两者分开 这样的思想分的包。在ui包下，可以建各个二级包表示某个功能的页面，比如：login包下 就是login相关的界面显示（activity，fragment）。
        adapter包由于也是驱动页面显示 所以也放在了该包下，所有的adapter都放在该包下，命名规则：功能+adapter 比如 CouponAdapter 优惠券列表的adapter

    util：
        该包下都是可以直接使用的一些工具类，工具类都放这里。
            bus：通知的工具类
            glide：glide的工具类
            rx：rx相关的工具封装类
viewImpl：所有MVP模式需要的界面显示方法接口定义的接口view 命名规则：功能+view 比如：LoginView

    widget:
            存放所有自定义View的包。


4：整个项目的规则

    1：所有的string都需要定义在values/strings下，不可直接写在代码中
    2：所有的color都需要定义在values/colors下，特殊情况除外
    3：布局xml中的dp sp 都需要定义在values/dimens下。dp_10 sp_12

    4：所有代码中的命名规则均以驼峰命名：比如： 变量：userInfo  类：LoginActivity 成员变量m开头 如mCountry

    5：控件id命名规则：
        控件缩写+功能  缩写即是所有单词首字母组合
        比如：
        TextView        缩写 tv
        Button          缩写 btn
        ImageView       缩写 iv
        RelativeLayout  缩写 rl
            比如：mBtnLogin 表示 登陆按钮 (xml 中下划线 btn_login)kotlin 中可以直接在xml 声明控件的成员变量 mBtnLogin

    6：Activity和Fragment命名规则：
        所有的Activiy命名都以 功能+Activity 方式，layout xml 对应的 activity_功能名称
            比如：LoginActivity 对应xml  activity_login
        同理Fragment： LoginFragment  对应xml  fragment_login

        类似：列表Adapter中的xml 以item+功能 比如：item_coupon 优惠券列表
             自定义view中xml 以 view_功能  比如：view_refresh_footer 自定义的刷新footer

    7：drawable命名：
        shape 以 shape开头
        selector以selector开头
8：资源图片只放一套：(.9除外)
        如果高保真是1080  资源图片放到 mipmap-xxhdpi
        如果高保真是750   资源图片放到 mipmap-xhdpi

    9：注释：
        所有的类需加上类说明的注释 表示该类的功能和作用
        比如：LoginActivity

                /**
                  * @Author wangchengm
                  * @desc 登陆页面
                */
        成员变量尽量规范命名，看名知用的效果，可以增加代码的可读性，比较复杂的逻辑 尽量对成员变量以及，方法加上说明注释，甚至对每一行代码加上注释。