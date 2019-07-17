package com.base.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.base.lib.R;

/**
 * @copyright : 深圳车发发科技有限公司
 * Created by yixf on 2017/12/23.
 * @description:
 */

public class CustomTextView extends AppCompatTextView {

    private GradientDrawable gd;
    //填充色
    private int solidColor = 0;
    //边框色
    private int strokeColor = 0;
    //按下填充色
    private int solidTouchColor = 0;
    //按下边框色
    private int strokeTouchColor = 0;
    //边框宽度
    private int strokeWith = 2;
    private int shapeTpe = GradientDrawable.RECTANGLE;
    //按下字体色
    private int textTouchColor=0;
    //字体色
    private int textColor=0;

    float dashGap=0;

    float dashWidth=0;

    public CustomTextView(Context context,int solidColor,int strokeColor,int radius) {
        super(context);

        init(solidColor,strokeColor,radius);
    }
    private void init(int solidColor,int strokeColor,int radius){
        this.solidColor = solidColor;
        this.strokeColor = strokeColor;

        gd = new GradientDrawable();
        gd.setShape(shapeTpe);
        //矩形
        if (shapeTpe == GradientDrawable.RECTANGLE) {
            gd.setShape(GradientDrawable.RECTANGLE);
            if (radius != 0) {
                gd.setCornerRadius(radius);
            }
        }
        gd.setColor(solidColor);
        gd.setStroke(strokeWith, strokeColor);
        setBackground(gd);
    }
    /**
     * 实现自定义圆角背景
     * 支持
     * 1.四边圆角
     * 2.指定边圆角
     * 3.支持填充色以及边框色
     * 4.支持按下效果
     */
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);

        solidColor = ta.getInteger(R.styleable.CustomTextView_textSolidColor, 0x00000000);
        strokeColor = ta.getInteger(R.styleable.CustomTextView_textStrokeColor, 0x00EEEEEE);

        solidTouchColor = ta.getInteger(R.styleable.CustomTextView_solidTouchColor, 0x00EEEEEE);
        strokeTouchColor = ta.getInteger(R.styleable.CustomTextView_strokeTouchColor, 0x00EEEEEE);
        textTouchColor= ta.getInteger(R.styleable.CustomTextView_textTouchColor, 0x00000000);
        textColor=getCurrentTextColor();
        strokeWith = ta.getInteger(R.styleable.CustomTextView_strokeWith, 2);

        float radius = ta.getDimension(R.styleable.CustomTextView_textRradius, 0);
        float topLeftRadius = ta.getDimension(R.styleable.CustomTextView_topLeftRadius, 0);
        float topRightRadius = ta.getDimension(R.styleable.CustomTextView_topRightRadius, 0);
        float bottomLeftRadius = ta.getDimension(R.styleable.CustomTextView_bottomLeftRadius, 0);
        float bottomRightRadius = ta.getDimension(R.styleable.CustomTextView_bottomRightRadius, 0);
        dashGap = ta.getDimension(R.styleable.CustomTextView_dashGap, 0);
        dashWidth = ta.getDimension(R.styleable.CustomTextView_dashWidth, 0);

        shapeTpe= ta.getInt(R.styleable.CustomTextView_shapeTpe, GradientDrawable.RECTANGLE);

        gd = new GradientDrawable();

        gd.setShape(shapeTpe);
        //矩形
        if (shapeTpe == GradientDrawable.RECTANGLE) {
            gd.setShape(GradientDrawable.RECTANGLE);
            if (radius != 0) {
                gd.setCornerRadius(radius);
            } else {
                //分别表示 左上 右上 右下 左下
                gd.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
            }
        }
        drowBackgroud(false);
        ta.recycle();
    }

    public void setSelection(boolean selection) {
        drowBackgroud(selection);
    }

    /**
     * 设置按下颜色值
     */
    private void drowBackgroud(boolean isTouch) {
        if (isTouch) {
            if (solidTouchColor != 0)
                gd.setColor(solidTouchColor);

            if (strokeWith != 0 && strokeTouchColor != 0) {
                if(dashGap!=0&&dashGap!=0)
                    gd.setStroke(strokeWith, strokeTouchColor,dashGap,dashGap);
                else
                    gd.setStroke(strokeWith, strokeTouchColor);
            }
            if(textTouchColor!=0)
                setTextColor(textTouchColor);
        } else {
            if (solidColor != 0)
                gd.setColor(solidColor);
            else
                gd.setColor(Color.TRANSPARENT);

            if (strokeWith != 0 && strokeColor != 0) {

                if(dashGap!=0&&dashGap!=0)
                    gd.setStroke(strokeWith, strokeColor,dashGap,dashGap);
                else
                    gd.setStroke(strokeWith, strokeColor);
            }else
                gd.setStroke(0, Color.TRANSPARENT);

            if(textTouchColor!=0)
                setTextColor(textColor);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(gd);
        }
        postInvalidate();
    }
    /**
     * 动态设置填充颜色
     * @param color
     */
    public void setTextSolidColor(int color){
        solidColor = color;

        drowBackgroud(false);
    }
    /**
     * 设置边框颜色
     * @param color
     */
    public void setTextStrokeColor(int color){
        strokeColor = color;
        drowBackgroud(false);
    }
    /**
     * 动态设置边框颜色
     */
    public void setTextStrokeAndSolidColor(int strokeColor,int solidColor){
        this.strokeColor = strokeColor;
        this.solidColor = solidColor;

        drowBackgroud(false);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            drowBackgroud(true);
//        } else {
//            drowBackgroud(false);
//        }
//
//        return false;
//    }
}
