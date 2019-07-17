package com.base.lib.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.util.Enumeration;
import java.util.Stack;

/*
 *
 * @copyright : 深圳市创冠新媒体网络传媒有限公司版权所有
 *
 * @author :yixf
 *
 * @version :1.0
 *
 * @creation date: 2017/3/23 0023
 *
 * @description:Activity管理
 *
 * @update date :
 */
public class AppManager {
    private static Stack<Activity> mActivityStack;
    private static AppManager mAppManager;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }
    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }
    /**
     * 移除activity栈
     * @param activity
     */
    public void removeActivity(Activity activity){
        if(activity!=null&&mActivityStack!=null){
            mActivityStack.remove(activity);
        }
    }
    public boolean ActivityStackIsEmpty() {
        if (mActivityStack != null && mActivityStack.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 获取界面数量
     *
     * @return activity size
     */
    public int getActivitySize() {
        if (mActivityStack != null) {
            return mActivityStack.size();
        }
        return 0;
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }
    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }
    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    /**
     * 检测Activity是否存在任务栈
     */
    public boolean checkActivity(Class<?> cls) {
        Enumeration<Activity> items = mActivityStack.elements();
        while (items.hasMoreElements()) {
            Activity tempActivity = items.nextElement();
            if (TextUtils.equals(cls.getSimpleName(), tempActivity.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        Enumeration<Activity> items = mActivityStack.elements();
        while (items.hasMoreElements()) {
            Activity tempActivity = items.nextElement();
            if (TextUtils.equals(cls.getSimpleName(), tempActivity.getClass().getSimpleName())) {
                killActivity(tempActivity);
            }
        }
    }
    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }
    /**
     * 返回程序首页
     */
    public void backHomeActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                if(!mActivityStack.get(i).getClass().getSimpleName().equals("MainActivity")){
                    mActivityStack.get(i).finish();
                }
            }
        }
    }
    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
