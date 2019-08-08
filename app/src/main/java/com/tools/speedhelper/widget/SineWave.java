package com.tools.speedhelper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.tools.speedhelper.R;
import com.tools.speedhelper.util.Util;
import java.util.ArrayList;
import java.util.List;

public class SineWave extends View {
    List<Integer> datas ; //
    private Paint mPaint = null;
    int centerStartingX; //
    public SineWave(Context context) {
        super(context);
        datas = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0x00686DE8);
        mPaint.setAlpha(200);
        mPaint.setStrokeWidth(3);
    }
    public SineWave(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, com.tools.speedlib.R.styleable.WaveView, 0, 0);
        datas = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(a.getColor(R.styleable.WaveView_sw_wave_color,0x00686DE8));
        mPaint.setAlpha(200);
        mPaint.setStrokeWidth(3);
    }
    public void Set(int frequency) {
        datas.add(frequency);
        reFresh();
    }
    /**
     * 设置折线颜色
     * @param color
     */
    public void setWaveColor(int color){
        mPaint.setColor(color);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        centerStartingX = Util.centerStartingX;
            for (int i = 0; i < datas.size() - 1; i++) {
                centerStartingX += Util.ScaleX;
                canvas.drawLine(centerStartingX, (float) (Util.centerEndY - datas.get(i)
                                * Util.spacingY), (float) (centerStartingX + Util.ScaleX),
                        (float) (Util.centerEndY - datas.get(i+1) * Util.spacingY), mPaint);
        }
    }
    public void clearData(){
        datas.clear();
    }
    public void reFresh() {
        this.invalidate();
    }
}
