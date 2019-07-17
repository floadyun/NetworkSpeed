package com.base.lib.http.okhttputils.builder;


import com.base.lib.http.okhttputils.OkHttpUtils;
import com.base.lib.http.okhttputils.request.OtherRequest;
import com.base.lib.http.okhttputils.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
