package com.tools.speedhelper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tools.speedhelper.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SineWave extends View {
    List<Integer> datas ; //
    private Paint mPaint = null;
    int centerStartingX, centerStartingY; //
    int centerEndX, centerEndY; //
    double ScaleX, ScaleY; //
    public SineWave(Context context) {
        super(context);
        datas = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setAlpha(200);
        mPaint.setStrokeWidth(5);
    }
    public SineWave(Context context, AttributeSet attrs) {
        super(context, attrs);
        datas = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setAlpha(200);
        mPaint.setStrokeWidth(5);
    }
    public void Set(int frequency) {
        datas.add(frequency);
        reFresh();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        centerStartingX = Util.centerStartingX;
            for (int i = 0; i < datas.size() - 1; i++) {
                if(i>0){
                    centerStartingX += Util.ScaleX;
                    canvas.drawLine(centerStartingX, (float) (Util.core - datas.get(i-1)
                                    * Util.spacingY), (float) (centerStartingX + Util.ScaleX),
                            (float) (Util.core - datas.get(i) * Util.spacingY), mPaint);
            }
        }
    }
    public void clearData(){
        datas.clear();
    }
    public void reFresh() {
        this.invalidate();
    }
}
