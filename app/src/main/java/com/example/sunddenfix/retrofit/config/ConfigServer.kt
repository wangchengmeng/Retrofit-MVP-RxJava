@file:JvmName("ConfigServer")
package com.example.sunddenfix.retrofit.config

/**
 * @author wangchengm
 * @desc 接口url 的管理类
 */
class ConfigServer {
    companion object {
        //TODO 发布切换环境 -1 本地dev调试  0 测试 1予发布 2 线上
        var API_INDEX = 0

        // 线上地址 2
        private const val API_BASE_URL: String = "https://sapi.xiaonianyu.com/"

        //线上的 api key
        private const val API_KEY: String = "ec480296721a1f549391fb616a0ca37f"

        //线上的api secret
        private const val API_SECRET: String = "71795f013d49ba0674fab783e32f8155"

        //预生产 1
        private const val API_RELEASE_BASE_URL: String = "https://pre-release.xiaonianyu.com/"

        //预生产 api key
        private const val API_KEY_RELEASE: String = "268472588ecd0907af3888289794b246"

        //预生产 secret
        private const val API_SECRET_RELEASE: String = "bf3e127a4a79a13e9cf07f3a270ca76d"

        //测试地址 0
        private const val API_TEST_DEVELOP_BASE_URL: String = "https://test-app.xiaonianyu.com/"

        //本地调试地址 -1
        private const val API_DEV_DEVELOP_BASE_URL: String = " https://dev-app.xiaonianyu.com/"

        // 测试的 api key
        private const val API_KEY_DEVELOP: String = "8a60f57f9d3bc3698e09abf4e7e1c217"

        //测试api secret
        private const val API_SECRET_DEVELOP: String = "b1800bc0171564850ea674087172aa7c"

        // 神策 数据接收地址 测试
        private const val SA_DEVELOP_SERVER_URL: String =
            "http://action-log-test.xiaonianyu.com/sa.php?project=xiaonianyu"

        // 神策 数据接收地址 正式
        private const val SA_RELEASE_SERVER_URL: String =
            "https://action-log.xiaonianyu.com/sa.php?project=xiaonianyu"

        /**
         * 获取app的 BaseUrl index ： 0 测试 1 予发布 2 正式
         */
        fun getAppBaseUrl(index: Int): String = when (index) {
            -1 -> {
                API_DEV_DEVELOP_BASE_URL
            }
            0 -> {
                API_TEST_DEVELOP_BASE_URL
            }
            1 -> {
                API_RELEASE_BASE_URL
            }
            else -> {
                API_BASE_URL
            }
        }

        /**
         * 获取app的 BaseUrl index ： 0 测试 1 予发布 2 正式
         */
        fun getApiKey(index: Int): String = when (index) {
            0, -1 -> {
                API_KEY_DEVELOP
            }
            1 -> {
                API_KEY_RELEASE
            }
            else -> {
                API_KEY
            }
        }

        /**
         * 获取app的 BaseUrl index ： 0 测试 1 予发布 2 正式
         */
        fun getApiSecret(index: Int): String = when (index) {
            0, -1 -> {
                API_SECRET_DEVELOP
            }
            1 -> {
                API_SECRET_RELEASE
            }
            else -> {
                API_SECRET
            }
        }

        //首页
        const val API_APP_MAIN_BANNER: String = "app/v6/api_banner"
        const val API_APP_MAIN_PORCELAIN: String = "app/v6/api_porcelain"
        const val API_APP_MAIN_SPECIAL_OFFER: String = "app/v6/api_index_category_info"
        const val API_APP_MAIN_NEW_EXCLUSIVE: String = "api/v1.0/goods/new_user"
        const val API_APP_MAIN_TOPIC: String = "app/v6/api_topic"
        const val API_APP_MAIN_LIMIT_SECOND: String = "app/v7/api_limit_goods_index"
        const val API_APP_MAIN_GOODS_TODAY: String = "app/v6/api_active"
        const val API_APP_MAIN_GET_NEW_COUPON: String = "app/v6/api_new_people_coupon_get"
        const val API_APP_MAIN_GET_RED_PACKAGE_STATUS: String = "app/v7/api_get_redpackage_status"
        const val API_APP_GET_APP_UPDATE_INFO: String = "app/v6/api_update"
        const val API_APP_GET_LIMIT_GOODS_LIST: String = "app/v7/api_limit_goods"
        const val API_APP_GET_MAIN_ACTIVE_LIST: String = "app/v6/api_spec_active"
        const val API_APP_GET_SEARCH_GOODS_LIST: String = "api/v1.0/search/goods"
        const val API_APP_GET_SEARCH_GOODS_NAME: String = "api/v1.0/search/suggest"
        const val API_APP_GET_SEARCH_HOTOWRD: String = "api/v1.0/search/hotword"
        const val API_APP_GET_SEARCH_DEFAULT_HOTOWRD_HINT: String = "api/v1.0/search/default"
        const val API_APP_GET_API_TOPIC_DETAIL: String = "app/v6/api_topic_detail"
        const val API_APP_GET_BRAND_GOODS_LIST: String = "app/v6/api_brand_goods_list"
        const val API_APP_DELETE_CAR_ITEM: String = "app/v6/api_cart"
        const val API_APP_GET_ORDER_STATUS: String = "app/v6/api_order_count"
        const val API_APP_GET_ORDER_LIST: String = "app/v6/api_order"
        const val API_APP_GET_RECOMMEND_GOODS: String = "app/v6/api_suggest_goods_list"
        const val API_APP_GET_ORDER_DETAIL: String = "app/v6/api_order/1"
        const val API_APP_GET_ORDER_LOGISTICS: String = "app/v6/api_first_express"
        const val API_APP_GET_ALTER_ORDER_STATUS: String = "app/v6/api_order/{orderId}"
        const val API_APP_GET_DELETE_ORDER: String = "app/v6/api_order_del"
        const val API_APP_GET_COLLECT_LIST: String = "app/v6/api_collect"
        const val API_APP_CENCEL_COLLECT: String = "app/v6/api_collect_cancel"
        const val API_APP_GET_COUPON_LIST: String = "app/v7/api_coupon_list"
        const val API_APP_GET_LOGISTICS_INFO: String = "app/v6/api_express"
        const val API_APP_GET_RETURN_LOGISTICS_INFO: String = "app/v6/api_change_return_express"
        const val API_APP_GET_COMMNET_GOODS: String = "app/v6/api_order_comment_get"
        const val API_APP_COMMON_UPLOAD_FILES: String = "app/v6/api_common_upload_files"
        const val API_APP_COMMON_SUBMIT: String = "app/v6/api_order_comment_submit"
        const val API_APP_GET_REFUND_DETAIL: String = "app/v6/api_change_datail"
        const val API_APP_GET_CANCEL_REFUND: String = "app/v6/api_change_cancel"
        const val API_APP_GET_EXPRESS_LIST: String = "app/v6/api_expresslist"
        const val API_APP_GET_CHANGE_EXPRESS_LIST: String = "app/v6/api_change_express_submit"
        const val API_APP_GET_PROMULGATE_STATUS: String = "app/v6/api_promulgate_status"
        const val API_APP_GET_ADVICE_TYPE: String = "app/v6/api_suggest_type"
        const val API_APP_UPLOAD_ADVICE: String = "app/v6/api_suggest_submit"
        const val API_APP_GET_SHOP_GOODS: String = "app/v6/api_supplier_goods"
        const val API_APP_GET_GOODS_DETAIL: String = "app/v7/api_goods_detail"
        const val API_APP_GET_GOODS_COUPON: String = "app/v8/api_goods_coupon"
        const val API_APP_GET_GOODS_COUPON_SPOT_PRICE: String = "app/v8/api_goods_price"
        const val API_APP_GET_COUPONE_COUPON: String = "app/v6/api_order_coupon"
        const val API_APP_GET_COMMENT_COLLECT: String = "app/v6/api_goods_comment_collect"
        const val API_APP_COLLECT: String = "app/v6/api_collect_save"
        const val API_APP_CANCEL_COLLECT: String = "app/v6/api_collect_cancel"
        const val API_APP_HIGH_COMMENT_LIST: String = "app/v6/api_goods_comment_high_list"
        const val API_APP_COMMENT_LIST: String = "app/v6/api_goods_comment_list"
        const val API_APP_ADD_SHOP_CAR: String = "app/v6/api_cart_save"
        const val API_APP_SERVICE_GUARANTE: String = "app/v6/api_goods_service_guarantee"
        const val API_APP_ORDER_GOODS: String = "app/v6/api_order_goods"
        const val API_APP_USER_ADDRESS: String = "app/v6/api_user_address_list"
        const val API_APP_ALTER_USER_ADDRESS: String = "app/v6/api_user_address_edit"
        const val API_APP_ADD_USER_ADDRESS: String = "app/v6/api_user_address_add"
        const val API_APP_DELETE_USER_ADDRESS: String = "app/v6/api_user_address_del"
        const val API_APP_USER_ADDRESS_INFO: String = "app/v6/api_user_address_info"
        const val API_APP_API_LOGIC_OPEN_DAY: String = "app/v6/api_logic_open_day"

        const val API_APP_COMMNET_PRISE: String = "app/v6/api_goods_comment_give_star"
        const val API_APP_COMMNET_CANCEL_PRISE: String = "app/v6/api_goods_comment_canal_star"
        const val API_APP_COMMNET_NUM: String = "/app/v6/api_goods_comment_nums"
        const val API_APP_CREATE_ORDER: String = "app/v8/api_order"
        const val API_APP_GET_WX_PAY: String = "app/v8/api_pay"
        const val API_APP_GET_AY_TYPE: String = "app/v8/app_payment/index"
        const val API_APP_GET_ALI_PAY: String = "app/v8/app_ali_pay/ali_pay"
        const val API_APP_GET_COMPANY_LIST: String = "app/v6/api_expresslist"
        const val API_APP_ALTER_CART_AMOUNT: String = "app/v6/api_cart_amount"
        const val API_APP_PLAQUE_ADVERTISEMENT: String = "api/v1.0/plaque"
        const val API_APP_LOGIN_OUT: String = "api/v1.0/user/logout"
        const val API_APP_ACTIVE_GIFT: String = "api/v1.0/gift"
        const val API_OUT_REFRESH_TOKEN: String = "api/v1.0/user/token"
        const val API_ACTIVE_DEVICE: String = "api/v1.0/device/active"
        const val API_NEW_CUSTOMER_CATE: String = "api/v1.0/goods/new_user/cat"
        const val API_GET_HOME_STATUE: String = "api/v1.0/activity/app/state"
        const val API_GET_DOUBLE_11_INFO: String = "api/v1.0/activity/app/info"
        const val API_GET_OPEN_SCREEN: String = "api/v1.0/open_screen"
        const val API_GET_LOTTERY_STATE: String = "api/v1.0/lottery/prize_state"
        const val API_GET_GOODS_NEW_CAT: String = "api/v1.0/goods/new/cat"
        const val API_GET_GOODS_NEW_HOT: String = "api/v1.0/goods/new"
        const val API_GET_SUBSIDY_GOODS: String = "api/v1.0/activity/subsidy/goods"
        const val API_GET_SUBSIDY_COUPON: String = "api/v1.0/activity/subsidy/coupon"
        const val API_GET_SUBSIDY_PRICE_DAY: String = "api/v1.0/activity/subsidy/price_day"
        const val API_REMIND_LIMIT: String = "api/v1.0/limit/alarm"
        const val API_GET_SUBSIDY_WINDOW: String = "api/v1.0/activity/subsidy/window"
        const val API_CLOSE_SUBSIDY_WINDOW: String = "api/v1.0/activity/subsidy/window"
        const val API_GET_HOME_NEW_GOODS: String = "api/v1.0/app/home"
        const val API_GET_HOME_NEW_GOODS_HOT: String = "api/v1.0/goods/hot_new"
        const val API_GET_HOME_WISH_LIST: String = "api/v1.0/activity/wish/window"
        const val API_GET_BASE_INFO: String = "app/v8/config/base"


    }
}