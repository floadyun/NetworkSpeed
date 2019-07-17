package com.base.lib.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @author :yixf
 * @creation date: 2017/4/21 0021
 * @description:土司工具类
 */
public class ToastUtil {
    /** 上下文. */
    private static Context mContext = null;

    /** 显示Toast. */
    public static final int SHOW_TOAST = 0;
    public static Toast mToast;

    /**
     * 主要Handler类，在线程中可用 what：0.提示文本信息
     */
    private static Handler baseHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast(mContext, msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 屏幕中间弹
     * @param context 上下文
     * @param text 文本
     */
    public static void showCenterToast(Context context, String text) {
        mContext = context;
        if (mContext != null && !TextUtils.isEmpty(text)) {
            initToastInstance();
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setText(text);
            mToast.show();
        }
    }
    /**
     * 屏幕中间弹
     * @param context 上下文
     * @param resId
     */
    public static void showCenterToast(Context context,int resId){
        mContext = context;
        if(mContext!=null){
            CharSequence text = mContext.getResources().getText(resId);
            showCenterToast(mContext,text.toString());
        }
    }
    /**
     * 描述：Toast提示文本
     * @param text
     *            文本
     */
    public static void showToast(Context context, String text) {
        try{
            mContext = context;
            if (mContext != null && !TextUtils.isEmpty(text)) {
                initToastInstance();
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.setText(text);
                mToast.show();
            }
        }catch (Exception e){

        }
    }
    /**
     * 描述：Toast提示文本.
     *
     * @param resId
     *            文本的资源ID
     */
    public static void showToast(Context context, int resId) {
        try {
            mContext = context;
            if(mContext!=null){
                CharSequence text = mContext.getResources().getText(resId);
                showToast(mContext,text.toString());
            }
        }catch (Exception e){

        }
    }
    /**
     * 描述：在线程中提示文本信息.
     *
     * @param resId
     *            要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context, int resId) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param text
     *            消息what值为0
     */
    public static void showToastInThread(Context context, String text) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }
    /**
     * 初始化吐司,保证数据的唯一性
     */
    private static void initToastInstance() {
        if (mToast == null) {
            synchronized (ToastUtil.class) {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
                }
            }
        }
    }
    /**
     * 带文字和图片的Toast
     * @param context 上下文
     * @param text 文字
     * @param resource 图片资源
     */
    public static void showToast(Context context,String text,int resource) {
        // TODO Auto-generated method stub
        mContext = context;
        Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if(toast.getView() instanceof RelativeLayout){
            RelativeLayout toastView = (RelativeLayout) toast.getView();
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(resource);
            toastView.addView(imageView,0);
        }else if(toast.getView() instanceof LinearLayout){
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(resource);
            toastView.addView(imageView,0);
        }
        toast.show();
    }

    /**
     * 展示带颜色背景的toast
     * @param context
     * @param text
     * @param color
     */
    public static void showToastByColor(Context context,String text,int color){
        try {
            mContext = context;
            if(mContext!=null){
                initToastInstance();
                mToast.getView().setBackgroundColor(mContext.getResources().getColor(color));
                showToast(mContext,text);
            }
        }catch (Exception e){

        }
    }
}
