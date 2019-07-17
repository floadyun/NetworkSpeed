package com.base.lib.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.base.lib.BuildConfig;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 使用Glide4.0显示带圆角的图片
 */
public class GlideRoundTransform
        extends BitmapTransformation {
    private Context context;
    private float mRadius;
    private CornerType mCornerType;
    private static final int VERSION = 1;
    private static final String ID = BuildConfig.APPLICATION_ID+"GlideRoundedCornersTransform." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);


    public enum CornerType {
        ALL,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT,
        TOP_LEFT_BOTTOM_RIGHT,
        TOP_RIGHT_BOTTOM_LEFT,
        TOP_LEFT_TOP_RIGHT_BOTTOM_RIGHT,
        TOP_RIGHT_BOTTOM_RIGHT_BOTTOM_LEFT,
    }

    public GlideRoundTransform(Context context,float radius, CornerType cornerType) {
        super();
        mRadius=dip2px(context,radius);//dp ->px
//        mRadius = UiUtils.dp2px(radius);//dp ->px
        mCornerType = cornerType;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);


        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config
                    .ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader
                .TileMode.CLAMP));
        paint.setAntiAlias(true);

        Path path = new Path();
        drawRoundRect(canvas, paint, path, width, height);

        return result;
    }

    private void drawRoundRect(Canvas canvas, Paint paint, Path path, int width, int height) {
        float[] rids ;
        switch (mCornerType) {
            case ALL:
                rids = new float[]{mRadius,mRadius,mRadius,mRadius,mRadius,mRadius,mRadius,mRadius};
                drawPath(rids,canvas, paint, path, width, height);
                break;
            case TOP_LEFT:
                rids = new float[]{mRadius,mRadius,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f};
                drawPath(rids,canvas, paint, path, width, height);
                break;
            case TOP_RIGHT:
                rids  = new float[]{0.0f,0.0f,mRadius,mRadius,0.0f,0.0f,0.0f,0.0f};
                drawPath(rids,canvas, paint, path, width, height);
                break;
            case BOTTOM_RIGHT:
                rids  = new float[]{0.0f,0.0f,0.0f,0.0f,mRadius,mRadius,0.0f,0.0f};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case BOTTOM_LEFT:
                rids  = new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,mRadius,mRadius};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case TOP:
                rids = new float[]{mRadius,mRadius,mRadius,mRadius,0.0f,0.0f,0.0f,0.0f};
                drawPath(rids,canvas,  paint,  path,width, height);
                break;
            case BOTTOM:
                rids  = new float[]{0.0f,0.0f,0.0f,0.0f,mRadius,mRadius,mRadius,mRadius};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case LEFT:
                rids = new float[]{mRadius,mRadius,0.0f,0.0f,0.0f,0.0f,mRadius,mRadius};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case RIGHT:
                rids  = new float[]{0.0f,0.0f,mRadius,mRadius,mRadius,mRadius,0.0f,0.0f};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case TOP_LEFT_BOTTOM_RIGHT:
                rids  = new float[]{mRadius,mRadius,0.0f,0.0f,mRadius,mRadius,0.0f,0.0f};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case TOP_RIGHT_BOTTOM_LEFT:
                rids  = new float[]{0.0f,0.0f,mRadius,mRadius,0.0f,0.0f,mRadius,mRadius};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case TOP_LEFT_TOP_RIGHT_BOTTOM_RIGHT:
                rids  = new float[]{mRadius,mRadius,mRadius,mRadius,mRadius,mRadius,0.0f,0.0f};
                drawPath(rids,canvas,  paint, path, width, height);
                break;
            case TOP_RIGHT_BOTTOM_RIGHT_BOTTOM_LEFT:
                rids  = new float[]{0.0f,0.0f,mRadius,mRadius,mRadius,mRadius,mRadius,mRadius};
                drawPath(rids,canvas,  paint,  path,width, height);
                break;
            default:
                throw new RuntimeException("RoundedCorners type not belong to CornerType");


        }
    }


    /**@param rids 圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private void drawPath(float[] rids, Canvas canvas, Paint paint, Path path, int width, int height) {
        path.addRoundRect(new RectF(0, 0, width, height), rids, Path.Direction.CW);
//        canvas.clipPath(path);
        canvas.drawPath(path,paint);
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof GlideRoundTransform;
    }


    @Override
    public int hashCode() {
        return ID.hashCode();
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}