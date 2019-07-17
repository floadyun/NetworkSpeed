package com.base.lib.http.base;


import android.content.Context;
import android.text.TextUtils;

import com.base.lib.baseui.AppBaseActivity;
import com.base.lib.http.ApiHelper;
import com.base.lib.http.network.NetworkUtils;
import com.base.lib.util.ToastUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by yixiaofei on 2017/3/9 0009.
 */
public abstract class BaseObserver<T> implements Observer<T>{

    private AppBaseActivity baseActivity;

    public BaseObserver(Context context) {
        this.baseActivity = (AppBaseActivity) context;
        try {
            if(!NetworkUtils.isConnected(baseActivity)){
                if(baseActivity!=null){
                    baseActivity.showToastText("无网络,请检检查网络连接");
                }
                return;
            }
        }catch (Exception exception){

        }
    }
    //请求成功回调
    public abstract void onSuccess(T value);
    @Override
    public void onSubscribe(Disposable d) {

    }
    @Override
    public void onNext(T value) {
        if(value!=null){
            if(value instanceof BaseEntity){
                BaseEntity baseEntity = (BaseEntity) value;
                if(((BaseEntity) value).code==null)return;
                if(baseEntity.code.equals(ApiHelper.SUCCESS_CODE)){
                    onSuccess(value);
                }else {
                    ToastUtil.showToast(baseActivity,baseEntity.msg);
                }
            }else{
                ToastUtil.showToast(baseActivity,"数据解析失败");
            }
        }
    }
    @Override
    public void onError(Throwable e) {
        try {
            if(baseActivity!=null){
                baseActivity.stopProgressDlg();
            }
            if(e instanceof HttpException){//其他状态异常 输出
                ResponseBody body = ((HttpException) e).response().errorBody();
                String errorStr = "";
                try {
                    errorStr = body.string();
                    Logger.e(errorStr);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    if(baseActivity!=null){
                        baseActivity.showToastText(exception.toString());
                    }
                }
                Gson gson = new Gson();
                switch (((HttpException) e).code()){
                    case ApiHelper.HTTP_401://登录失效，重新登录
                        if(baseActivity!=null){
                            baseActivity.reLogin();
                        }
                        break;
                    case ApiHelper.HTTP_422:
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(errorStr);
                            JSONObject errorObject = jsonObject.getJSONObject("errors");
                            //通过迭代器获取这段json当中所有的key值
                            Iterator keys = errorObject.keys();
                            if(!keys.hasNext())return;
                            String key = String.valueOf(keys.next());
                            String value = errorObject.optString(key);
                            if(!TextUtils.isEmpty(value)){
                                if(baseActivity!=null){
                                    baseActivity.showToastText(value.substring(1,value.length()-1).replace("\"",""));
                                }
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case ApiHelper.HTTP_423:
                        BaseEntity baseEntity = gson.fromJson(errorStr,BaseEntity.class);
                        if(baseEntity!=null){
                            if(baseActivity!=null){
                                baseActivity.showToastText(baseEntity.msg);
                            }
                        }
                        break;
                    default:
                        if(baseActivity!=null){
                                baseActivity.showToastText("请求出错，请稍后再试");
                        }
                        break;
                }
            }else {
                if(baseActivity!=null){
                    baseActivity.showToastText("请求出错，请稍后再试");
                }
            }
        }catch (Exception exception){
            if(baseActivity!=null){
                baseActivity.showToastText(exception.toString());
            }
        }
    }
    @Override
    public void onComplete() {

    }
}
