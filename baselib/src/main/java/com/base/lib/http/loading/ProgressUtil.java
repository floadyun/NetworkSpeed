package com.base.lib.http.loading;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by yixiaofei on 2017/3/9 0009.
 */

public class ProgressUtil {

    static ProgressDialog progressDlg = null;
    /**
     * 启动进度条
     *
     * @param strMessage
     *            进度条显示的信息
     *            当前的activity
     */
    public static void showProgressDlg(Context context, String title, String strMessage) {

        if (null == progressDlg) {
            progressDlg = new ProgressDialog(context);
            //设置进度条样式
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //设置进度条标题
            progressDlg.setTitle(title);
            //提示的消息
            progressDlg.setMessage(strMessage);
            progressDlg.setIndeterminate(false);
//            progressDlg.setCancelable(false);
            progressDlg.show();
        }
    }
    /**
     * 启动进度条
     *
     *            进度条显示的信息
     *            当前的activity
     */
    public static void showProgressDlg(Context context) {
        if (null == progressDlg) {
            progressDlg = new ProgressDialog(context);
            //设置进度条样式
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //设置进度条标题
//            progressDlg.setTitle("");
            //提示的消息
            progressDlg.setMessage("正在加载...");
            progressDlg.setIndeterminate(false);
            progressDlg.setCancelable(false);
            progressDlg.show();
        }
    }

    /**
     * 动态设置progress进度
     * @param text
     */
    public static void setProressText(String text){
        if(progressDlg!=null&&progressDlg.isShowing()){
            progressDlg.setMessage(text);
        }
    }
    /**
     * 结束进度条
     */
    public static void stopProgressDlg() {
        if (null != progressDlg) {
            progressDlg.dismiss();
            progressDlg = null;
        }
    }
}
