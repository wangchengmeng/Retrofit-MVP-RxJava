package com.example.sunddenfix.retrofit.model;

/**
 * @author wangchengmeng
 */
public class ActionResult<T> {

    /**
     * 接口正常
     */
    public static final String RESULT_CODE_SUCCESS   = "0";
    /**
     * 网络异常
     */
    public static final String RESULT_CODE_NET_ERROR = "111";
    /**
     * 未授权
     */
    public static final String RESULT_CODE_NO_AUTH   = "401";
    /**
     * token过期状态
     */
    public static final String RESULT_CODE_NO_LOGIN  = "403";
    /**
     * 页面未发现
     */
    public static final String RESULT_CODE_NO_FOUND  = "404";

    /**
     * 一般返回数据格式：
     * code：200
     * data：{} 或者[]
     * message：返回信息
     */
    private String code;
    private String message;
    private T      data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ActionResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", Object=" + data +
                '}';
    }
}
