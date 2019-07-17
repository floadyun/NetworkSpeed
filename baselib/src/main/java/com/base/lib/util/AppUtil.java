package com.base.lib.util;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.text.TextUtils;

import com.base.lib.http.ApiHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @copyright : 深圳车发发科技有限公司
 * Created by yixf on 2017/11/14.
 * @description:App构建工具类
 */
public class AppUtil {
    /**
     * 腾讯包名
     */
    public static final String TX_MAP_PACKAGE_NAME = "com.tencent.map";
    /**
     * 高德包名
     */
    public static final String GD_MAP_PACKAGE_NAME = "com.autonavi.minimap";
    /**
     * 百度地图
     */
    public static final String BD_MAP_PACKAGE_NAME = "com.baidu.BaiduMap";

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isMobilePhone(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3456789]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    /**
     * 判断车牌号
     * @param carNumber
     * @return
     */
    public static boolean isCarNumber(String carNumber){
        if(carNumber.length()<7){
            return false;
        }
        /*
          3 车牌号格式：汉字 + A-Z + 5位A-Z或0-9
          4 （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
          5
        */
        String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        String carnumRegex1 = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{6}";
        if (TextUtils.isEmpty(carNumber)) return false;
        else return carNumber.matches(carnumRegex)||carNumber.matches(carnumRegex1);
    }
    /**
     * 密码验证是否为8-24位数字+字母组合
     * @param password
     * @return
     */
    public static boolean isVerifyPassword(String password){
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,24}$";
        return password.matches(regex);
    }
    /**
     * 获取当前应用版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        String version = "";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    /**
     * 获取当前应用版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        int versionCode = 0;
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 检测是否安装app
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }
    /**
     * 检测是否支持指纹解锁
     * @return
     */
    @TargetApi(23)
    public static boolean supportFingerprint(Context context) {
        if (Build.VERSION.SDK_INT <23) {
//            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try{
                KeyguardManager keyguardManager = context.getSystemService(KeyguardManager.class);
                FingerprintManager fingerprintManager = context.getSystemService(FingerprintManager.class);
                if (!fingerprintManager.isHardwareDetected()) {
//                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (!keyguardManager.isKeyguardSecure()) {
//                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (!fingerprintManager.hasEnrolledFingerprints()) {
//                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
