package com.base.lib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yxf on 2017/1/23.
 * SharedPrefrence存储类
 */

public class PreferenceUtil {

    private static PreferenceUtil preferenceUtil;

    private static SharedPreferences sharedPreferences;

    private static final String YQK_PREFERENCES = "xtw_preferences";

    private Context context;

    public PreferenceUtil(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(YQK_PREFERENCES,Context.MODE_PRIVATE);
    }
    /**
     * 单例返回
     * @return
     */
    public synchronized static PreferenceUtil getPreference(Context context){
        if(preferenceUtil == null){
            preferenceUtil = new PreferenceUtil(context);
        }
        return preferenceUtil;
    }

    /**
     * 设置Preference的值
     * @param key
     * @param value
     */
    public void setStringPreference(String key,String value){
        sharedPreferences.edit().putString(key,value).commit();
    }
    /**
     * 设置Preference的值
     * @param key
     * @param value
     */
    public void setInPreference(String key,int value){
        sharedPreferences.edit().putInt(key,value).commit();
    }
    /**
     * 设置Preference的值
     * @param key
     * @param value
     */
    public void setLongPreference(String key,long value){
        sharedPreferences.edit().putLong(key,value).commit();
    }
    /**
     * 通过传入的key，返回相应的值
     * @param key
     * @return
     */
    public long getLongPreference(String key,long defaultValue){

        return sharedPreferences.getLong(key,defaultValue);
    }
    /**
     * 设置Preference的浮点值
     * @param key
     * @param value
     */
    public void setFloatPreference(String key,float value){
        sharedPreferences.edit().putFloat(key,value).commit();
    }
    /**
     * 通过传入的key，返回相应的值
     * @param key
     * @return
     */
    public String getStringPreference(String key,String defaultValue){

        return sharedPreferences.getString(key,defaultValue);
    }

    /**
     * 通过传入的key，返回相应的值
     * @param key
     * @return
     */
    public int getIntPreference(String key,int defaultValue){

        return sharedPreferences.getInt(key,defaultValue);
    }
    /**
     * 通过传入的key，返回相应的值
     * @param key
     * @return
     */
    public Float getFloatPreference(String key,float defaultValue){

        return sharedPreferences.getFloat(key,defaultValue);
    }

    /**
     * 保存boolean类型的值
     * @param key
     * @param value
     */
    public void setBoolPreference(String key,boolean value){
        sharedPreferences.edit().putBoolean(key,value).commit();
    }
    /**
     * 返回保存的boolean值
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBoolPreference(String key,boolean defaultValue){
        return sharedPreferences.getBoolean(key,defaultValue);
    }
}
