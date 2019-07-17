package com.base.lib.http;

public class ApiHelper {
    //正式环境
//    public static final String BASE_URL = "http://business.xitouwang.com/";
//    测试域名
    public static final String BASE_URL = "http://business.xitouwang.com:8888/";
    //测试域名
//    public static final String BASE_URL = "http://192.168.0.188:8088/";
    public static final String API_URL =BASE_URL+"api/";
    //更新链接
    public static String UPDATE_URL = BASE_URL+"app/getInfo";
    /**
     * 签约绑卡
     */
    public static final String BIND_CARD_URL=BASE_URL+"home#/userBind";
    //请求成功码
    public static final String SUCCESS_CODE = "200";
    //登录失效
    public static final int HTTP_401 = 401;
    //请求信息不完整
    public static final int HTTP_422 = 422;
    //请求信息完整，执行结果错误
    public static final int HTTP_423 = 423;

    public static final String PAGE_LIMIT_NUMBER = "10";
}
