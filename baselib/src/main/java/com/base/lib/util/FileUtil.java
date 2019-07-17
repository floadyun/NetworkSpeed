package com.base.lib.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.IntBuffer;

/**
 * Created by yixiaofei on 2017/3/25 0025.
 */

public class FileUtil {

    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static File createfile(String filePath){
        File mFile = new File(filePath);
        if(!mFile.exists()){
            mFile.getParentFile().mkdirs();
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mFile;
    }
    public static void saveFile(String filePath,byte[] bytes){
        FileOutputStream output = null;
        try {
            //SD卡是否存在
            if(!isCanUseSD()){
                return;
            }
            output = new FileOutputStream(createfile(filePath));
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 保存文件至内存
     * @throws Exception
     */
    public static void saveToData(Context context,String filePath,String filecontent){
        try {
            FileOutputStream outStream  = context.openFileOutput(filePath, Context.MODE_PRIVATE);
            outStream.write(filecontent.getBytes());
            outStream.close();
        }catch (Exception e){
            System.out.println("saveToSDCard........."+e.toString());
        }
    }
    /** 保存到SD卡
     * @throws Exception
     */
    public static void saveToSDCard(String filePath,String filecontent){
        try {
            File file = createfile(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(filecontent.getBytes());
            outStream.close();
        }catch (Exception e){
            System.out.println("saveToSDCard........."+e.toString());
        }
    }
    /**
     * 描述：将byte数组写入文件.
     *
     * @param path the path
     * @param content the content
     */
    public static String writeByteArrayToSD(String path, byte[] content){

        FileOutputStream fos = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if(!isCanUseSD()){
                return "";
            }
            //文件是否存在
            if(!file.exists()){
                File parent = file.getParentFile();
                if(!parent.exists()){
                    parent.mkdirs();
                    file.createNewFile();
                }
            }
            fos = new FileOutputStream(path);
            fos.write(content);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }finally{
            if(fos!=null){
                try {
                    fos.close();
                } catch (Exception e) {
                    return "";
                }
            }
        }
        return path;
    }

    /**
     * 保存图片到指定文件夹
     *
     * @param bmp
     * @param path
     * @return
     */
    public static void saveBitmapToFile(Bitmap bmp, String path,int quality) {
        if (bmp == null || path == null)
            return ;
        OutputStream stream = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if(!isCanUseSD()){
                return ;
            }
            //文件是否存在
            if(!file.exists()){
                File parent = file.getParentFile();
                if(!parent.exists()){
                    parent.mkdirs();
                    file.createNewFile();
                }
            }
            stream = new FileOutputStream(path);
            bmp.compress(Bitmap.CompressFormat.JPEG, quality,stream);
            stream.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void saveBitmapToFile(Bitmap bitmap,String path){
        File file=new File(path);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(path+"/"+System.currentTimeMillis()+".png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取指定文件大小
     * @param file
     * @return　　
     */
    public static long getFileSize(File file){
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            }
        }catch (Exception e){
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }
    public static Bitmap grabPixels(int x, int y, int w, int h)
    {
        int b[] = new int[w * (y + h)];
        int bt[] = new int[w * h];

        IntBuffer ib = IntBuffer.wrap(b);
        ib.position(0);
        GLES20.glReadPixels(x, 0, w, y + h,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);

        for (int i = 0, k = 0; i < h; i++, k++) {
            for (int j = 0; j < w; j++) {
                int pix = b[i * w + j];
                int pb = (pix >> 16) & 0xff;
                int pr = (pix << 16) & 0x00ff0000;
                int pix1 = (pix & 0xff00ff00) | pr | pb;
                bt[(h - k - 1) * w + j] = pix1;
            }
        }

        Bitmap sb = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
        return sb;
    }

    /**
     * 判断文件是否存在
     * @param filepath
     * @return
     */
    public static boolean isExistsFile(String filepath) {
        try {
            if (TextUtils.isEmpty(filepath)) {
                return false;
            }
            File file = new File(filepath);
            return file.exists();
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("FileUtil", "the file is not exists file path is: " + filepath);
            return false;
        }
    }
    /**
     * 删除file
     *
     */
    public static void deleteFile(String path) {
        if(null==path){
            return;
        }
        File file=new File(path);
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            }
        } else {

        }
    }
    /* 将图像保存到Data目录 */
    public static boolean saveBitmapToData(Activity act, Bitmap bmpToSave, String FileNameWithoutExtension,int Quality)
    {//参数依次为：调用的 Activity，需要写入 data 的位图，文件名（不含扩展名），扩展名，图像质量
        try
        {
            if (Quality > 100)
                Quality = 100;
            else if (Quality < 1)
                Quality = 1;
            FileOutputStream fos = act.openFileOutput(FileNameWithoutExtension + ".jpg" , Context.MODE_PRIVATE); //这里是关键，其实就是一个不含路径但包含扩展名的文件名
//            if (ext == Extension.png)
//                bmpToSave.compress(Bitmap.CompressFormat.PNG, Quality, fos);
//            else if (ext == Extension.jpeg)
                bmpToSave.compress(Bitmap.CompressFormat.JPEG, Quality, fos);
            //写入文件
            fos.flush();
            fos.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    /* 从Data目录读取图像 */
    public static Bitmap getBitmapFromData(Activity act, String FileName)
    {
            FileInputStream fis = null;
            try
            {
                fis = act.openFileInput(FileName);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            BufferedInputStream bis = new BufferedInputStream(fis);
            Bitmap bmpRet = BitmapFactory.decodeStream(bis);
            try
            {
                bis.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                fis.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        return bmpRet;
    }

    /**
     * 通过文件路径获取uri地址
     * @param context
     * @param filePath
     * @return
     */
    public static Uri getUriByPath(Context context,String filePath){
        File file = new File(filePath);
        Uri uri = null;
        if(file!=null){
            // 判断版本大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
                uri = FileProvider.getUriForFile(context, "com.chefafa.mall.fileprovider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            return uri;
        }
        return uri;
    }
    /**
     * 删除文件夹和文件夹里面的文件
     */
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
//        deleteDirWihtFile(dir);
        deleteAllFiles(dir);
    }
    static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
    public static String extractFileNameFromURL(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
