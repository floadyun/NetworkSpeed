package com.base.lib.baseui;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.lib.R;
import com.base.lib.glide.GlideManage;
import com.base.lib.util.AppManager;
import com.base.lib.util.PreferenceUtil;
import com.base.lib.util.ToastUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * @copyright : 深圳车发发科技有限公司
 * Created by yixf on 2017/11/13.
 * @description:所有activity基类
 */

public class AppBaseActivity extends AppCompatActivity {
    /**
     * 权限请求返回接口
     */
    public interface PermissionsResultListener {
        void onSuccessful(int[] results);
        void onFailure();
    }

    protected String pageName;
    /**
     * 加载进度对象
     */
    private KProgressHUD kProgressHUD;
    private int mRequestCode;
    private PermissionsResultListener mListener;
    private List<String> mListPermissions;
    //权限请求
    protected RxPermissions rxPermissions;
    public ActivityOptionsCompat compat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        rxPermissions = new RxPermissions(this);
        mListPermissions = new ArrayList<>();
        compat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_right_to_left, R.anim.slide_left_to_left);
    }
    /**
     * 检查权限
     * @param permissions
     * @param requestCode
     * @param listener
     */
    public void checkPermissions(String[] permissions, int requestCode, PermissionsResultListener listener) {
        mListener = listener;
        if (Build.VERSION.SDK_INT >= 23) {
            //权限不能为空
            if (permissions != null || permissions.length != 0) {
                mRequestCode = requestCode;
                for (int i = 0; i < permissions.length; i++) {
                    if (!isHavePermissions(permissions[i])) {
                        mListPermissions.add(permissions[i]);
                    }
                }
                //遍历完后申请
                applyPermissions();
            }
        }else{
            mListener.onSuccessful(null);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (grantResults.length > 0) {
                mListener.onSuccessful(grantResults);
            } else {
                mListener.onFailure();
            }
        }
    }
    //判断权限是否申请
    protected boolean isHavePermissions(String permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    //申请权限
    private void applyPermissions() {
        if (!mListPermissions.isEmpty()) {
            int size = mListPermissions.size();
            ActivityCompat.requestPermissions(this, mListPermissions.toArray(new String[size]), mRequestCode);
        }else{
            mListener.onSuccessful(null);
        }
    }
    /**
     * 信息提示
     *
     * @param text
     */
    public void showToastText(String text) {
        try {
            ToastUtil.showCenterToast(getApplicationContext(), text);
        }catch (Exception e){

        }
    }
    public void showToastText(int resId) {
        ToastUtil.showCenterToast(getApplicationContext(), resId);
    }
    public void showToastByColor(String text,int colorId){
        ToastUtil.showToastByColor(getApplicationContext(),text,colorId);
    }
    /**
     * 获取颜色值
     * @param colorId
     * @return
     */
    public int getResourceColor(int colorId){
        return getResources().getColor(colorId);
    }
    /**
     * 获取string
     * @param stringId
     * @return
     */
    public String getResourceString(int stringId){
        return getResources().getString(stringId);
    }
    /**
     * 保存SharePreference中String值
     * @param key
     * @return
     */
    public void saveStringPreference(String key,String defaultValue){
        PreferenceUtil.getPreference(getApplicationContext()).setStringPreference(key,defaultValue);
    }
    /**
     * 获取SharePreference中String值
     * @param key
     * @return
     */
    public String getStringPreference(String key,String defaultValue){
        return PreferenceUtil.getPreference(getApplicationContext()).getStringPreference(key,defaultValue);
    }
    /**
     * 获取SharePreference中boolean值
     * @param key
     * @return
     */
    public boolean getBooleanPreference(String key){
        return PreferenceUtil.getPreference(getApplicationContext()).getBoolPreference(key,false);
    }
    /**
     * 获取SharePreference中int值
     * @param key
     * @return
     */
    public int getIntegerPreference(String key){
        return PreferenceUtil.getPreference(getApplicationContext()).getIntPreference(key,0);
    }

    /**
     * 设置image
     * @param imageId
     * @param imageUrl
     */
    protected void setImageUrl(int imageId,String imageUrl){
        GlideManage.getInstance().with(this, R.mipmap.ic_launcher,imageUrl,(ImageView)findViewById(imageId));
    }
    /**
     * 设置TextView内容
     * @param textId
     * @param content
     */
    protected void setTextById(int textId,String content){
        ((TextView)findViewById(textId)).setText(content);
    }
    /**
     * 设置TextView颜色
     * @param textId
     * @param color
     */
    protected void setTextColorById(int textId,int color){
        ((TextView)findViewById(textId)).setTextColor(getResourceColor(color));
    }

    /**
     * 设置View是否可见
     * @param viewId
     * @param visibility
     */
    protected void setViewVisible(int viewId,int visibility){
        findViewById(viewId).setVisibility(visibility);
    }

    public void showLoadingView(){

    }
    public void hideLoadingView(){

    }
    /**
     * 登录失效，重新登录
     */
    public void reLogin(){

    }
    /**
     * 显示loading框
     * @param title
     * @param strMessage
     */
    public void showProgressDlg(String title, String strMessage) {
         if (null == kProgressHUD&&!isFinishing()) {
             kProgressHUD  = KProgressHUD.create(this)
             .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
             .setCancellable(true)
             .setAnimationSpeed(2)
             .setDimAmount(0.5f)
             .show();
             if(!title.isEmpty()){
                kProgressHUD.setLabel(title);
             }
             if(!strMessage.isEmpty()){
                kProgressHUD.setDetailsLabel(strMessage);
             }
         }
    }
    /**
     * 结束进度条
     */
    public void stopProgressDlg() {
        if (null != kProgressHUD) {
             kProgressHUD.dismiss();
             kProgressHUD = null;
        }
        hideLoadingView();
    }
    /**
     * 启动进度条
     *
     * @param title
     */
    public void showProgressDlg(String title) {
        try {
            showProgressDlg(title, "");
        } catch (Exception e) {

        }
    }
    /**
     * 启动进度条
     * 进度条显示的信息
     * 当前的activity
     */
    public void showProgressDlg() {
        try {
            showProgressDlg("", "");
        } catch (Exception e) {

        }
    }
    /***
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        dismissKeyboard();
        return true;
    }
    /**
     * 第三方分享
     *
     * @param shareUrl    分享链接
     * @param shareTitle  分享标题
     * @param imageUrl    分享图片路径，微博必须
     * @param description 分享描述
     */
    public void showShare(String shareUrl, String shareTitle, String imageUrl, String description, int defaultImage) {
//        ShareFragment shareFragment = new ShareFragment();
//        shareFragment.setShareText(this, shareUrl, shareTitle, imageUrl, description, defaultImage);
//        shareFragment.show(getSupportFragmentManager(), "ShareFragment");
    }
    /**
     * 隐藏键盘
     */
    protected void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        try {
            if (null != this.getCurrentFocus().getWindowToken()) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }
    @Override
    public void onBackPressed() {
        finishSelf();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
    }
    //字体适配解决方案
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
    protected void finishSelf() {
        dismissKeyboard();
        finish();
        overridePendingTransition(R.anim.slide_left_to_left_in, R.anim.slide_left_to_right);
    }
}
