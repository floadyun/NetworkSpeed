package com.base.lib.util;
/*
 *
 * @copyright : 深圳市创冠新媒体网络传媒有限公司版权所有
 *
 * @author :yixiaofei
 *
 * @version :1.0
 *
 * @creation date: 2017/5/24 0024
 *
 * @description:${desc}
 *
 * @update date :
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class BitmapUtil {

    public static int MIN_WIDTH = 100;

    /**
     * 图像灰度化处理
     * @param bitmap
     * @return
     */
    public static Bitmap convertToGrayImage(Bitmap bitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        Paint paint = new Paint();
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(cmcf);

        Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.RGB_565);

        Canvas drawingCanvas = new Canvas(result);
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(src);
        drawingCanvas.drawBitmap(bitmap, src, dst, paint);

        return result;
    }
    /**
     * 通过资源id转化成Bitmap
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitmapById(Context context, int resId)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
    /**
     * 设置背景为圆角
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap removeYuanjiao(Bitmap bitmap, int pixels)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap creBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(creBitmap);

        Paint paint = new Paint();
        float roundPx = pixels;
        RectF rectF = new RectF(0, 0, bitmap.getWidth() - pixels,
                bitmap.getHeight() - pixels);
        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (!bitmap.isRecycled())
            bitmap.recycle();

        return creBitmap;
    }
    /**
     * 按正方形裁切图片
     */
    public static Bitmap cropImage(Bitmap bitmap, boolean isRecycled)
    {
        if (bitmap == null)
        {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
        int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
                false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }

        // 下面这句是关键
        return bmp;// Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
        // false);
    }
    /**
     * 按长方形裁切图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap ImageCropWithRect(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return null;
        }

        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int nw, nh, retX, retY;
        if (w > h)
        {
            nw = h / 2;
            nh = h;
            retX = (w - nw) / 2;
            retY = 0;
        } else
        {
            nw = w / 2;
            nh = w;
            retX = w / 4;
            retY = (h - w) / 2;
        }

        // 下面这句是关键
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
                false);
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;// Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
        // false);
    }
    /**
     * 将图像裁剪成圆形
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height)
        {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else
        {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
        return output;
    }
    // 将图片变成带圆边的圆形图片
    public static Bitmap getRoundBitmap(Bitmap bitmap, int width, int height)
    {
        if (bitmap == null)
        {
            return null;
        }
        // 将图片变成圆角
        Bitmap roundBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int len = (width > height) ? height : width;
        canvas.drawCircle(width / 2, height / 2, len / 2 - 8, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, len, len, true);
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);
        // 将图片加圆边
        Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(outBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffffffff);
        canvas.drawCircle(width / 2, height / 2, len / 2 - 4, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(roundBitmap, 0, 0, paint);
        bitmap.recycle();
        bitmap = null;
        roundBitmap.recycle();
        roundBitmap = null;
        scaledBitmap.recycle();
        scaledBitmap = null;
        return outBitmap;
    }
    // 将图片变成带圆边的圆形图片
    public static Bitmap getRoundBitmap(Bitmap bitmap, int width, int height,
                                        int color)
    {
        if (bitmap == null)
        {
            return null;
        }
        // 将图片变成圆角
        Bitmap roundBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int len = (width > height) ? height : width;
        canvas.drawCircle(width / 2, height / 2, len / 2 - 8, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, len, len, true);
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);
        // 将图片加圆边
        Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(outBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        canvas.drawCircle(width / 2, height / 2, len / 2 - 4, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(roundBitmap, 0, 0, paint);
        bitmap.recycle();
        bitmap = null;
        roundBitmap.recycle();
        roundBitmap = null;
        scaledBitmap.recycle();
        scaledBitmap = null;
        return outBitmap;
    }
    /**
     * function:图片转圆角
     *
     * @param bitmap
     *            需要转的bitmap
     * @param pixels
     *            转圆角的弧度
     * @return 转圆角的bitmap
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
        }
        return output;
    }
    /**
     * 获取指定的圆角图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRadiusBitmap(Bitmap bitmap)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffffffff);
        Bitmap radiusBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(radiusBitmap);
        RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawRoundRect(rectF, 7, 7, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
        }
        return radiusBitmap;
    }

    /**
     * 缩放图片
     * @param org
     * @param scaleWidth
     * @param scaleHeight
     * @return
     */
    /**
     * 使用Matrix将Bitmap压缩到指定大小
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 二进制数据转换为bitmap
     * @param imgByte
     * @return
     */
    public static Bitmap byteToBitmap(byte[] imgByte) {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }
    // 获得指定大小的圆边的bitmap数组
    public static ArrayList<Bitmap> getRadiusBitmapList(String[] pathArray,
                                                        int size, int len, float radius, int color)
    {
        Bitmap canvasBitmap = null;
        Canvas canvas = null;
        Paint paint = null;
        RectF rectF = new RectF(0, 0, len - radius, len - radius);
        File file = null;
        FileInputStream fis = null;
        Bitmap bitmap = null;
        Bitmap scaledBitmap = null;
        ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        for (int i = 0; i < pathArray.length; i++)
        {
            file = new File(pathArray[i]);
            if (!file.exists())
                continue;
            try
            {
                fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
                if (bitmap != null)
                {
                    canvasBitmap = Bitmap.createBitmap(len, len,
                            Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(canvasBitmap);
                    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setColor(color);
                    canvas.drawRoundRect(rectF, radius, radius, paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

                    scaledBitmap = Bitmap.createScaledBitmap(bitmap, len, len,
                            true);
                    canvas.drawBitmap(scaledBitmap, 0, 0, paint);
                    list.add(canvasBitmap);
                }
            } catch (FileNotFoundException e)
            {
            } finally
            {
                if (fis != null)
                {
                    try
                    {
                        fis.close();
                    } catch (IOException e1)
                    {
                    }
                }
            }
            if (list.size() == size)
                break;
        }
        if (scaledBitmap != null && !scaledBitmap.isRecycled())
        {
            scaledBitmap.recycle();
            scaledBitmap = null;
        }
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
        return list;
    }
    /**
     * 按照一定的宽高比例裁剪图片
     *
     * @param bitmap
     * @param num1
     *            长边的比例
     * @param num2
     *            短边的比例
     * @return
     */
    public static Bitmap ImageCrop(Bitmap bitmap, int num1, int num2,
                                   boolean isRecycled)
    {
        if (bitmap == null)
        {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int retX, retY;
        int nw, nh;
        if (w > h)
        {
            if (h > w * num2 / num1)
            {
                nw = w;
                nh = w * num2 / num1;
                retX = 0;
                retY = (h - nh) / 2;
            } else
            {
                nw = h * num1 / num2;
                nh = h;
                retX = (w - nw) / 2;
                retY = 0;
            }
        } else
        {
            if (w > h * num2 / num1)
            {
                nh = h;
                nw = h * num2 / num1;
                retY = 0;
                retX = (w - nw) / 2;
            } else
            {
                nh = w * num1 / num2;
                nw = w;
                retY = (h - nh) / 2;
                retX = 0;
            }
        }
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
                false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;// Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
        // false);
    }
    /**
     * 按最大边按一定大小缩放图片
     *
     * @param resources
     * @param resId
     * @param maxSize 压缩后最大长度
     * @return
     */
    public static Bitmap scaleImage(Resources resources, int resId, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxSize, maxSize);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 计算inSampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (width < MIN_WIDTH) {
            return inSampleSize;
        } else {
            int heightRatio;
            if (width > height && reqWidth < reqHeight || width < height && reqWidth > reqHeight) {
                heightRatio = reqWidth;
                reqWidth = reqHeight;
                reqHeight = heightRatio;
            }

            if (height > reqHeight || width > reqWidth) {
                heightRatio = Math.round((float) height / (float) reqHeight);
                int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
            }

            return inSampleSize;
        }
    }

    /**
     * vuforia截图
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public static Bitmap createBitmapFromGLSurface(int x, int y, int w, int h, GL10 gl) {
        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);
        try {
            gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE,
                    intBuffer);
            int offset1, offset2;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            return null;
        }
        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
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
     * drawable为bitmap
     * @param drawable
     * @return
     */
    public static Bitmap getBitmapByDrawable(Drawable drawable){
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 通过图片路径获取bitmap
     * @param path
     * @return
     */
    public static Bitmap getBitmapByPath(String path){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       return BitmapFactory.decodeStream(fis);
    }
}
