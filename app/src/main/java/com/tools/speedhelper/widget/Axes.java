package com.tools.speedhelper.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import com.tools.speedhelper.util.Util;

public class Axes extends View {
	private static final int VAULE_X=100,VALUE_Y = 1000;
	int centerStartingX, centerStartingY;//中间开始位置
	int centerEndX, centerEndY;//中间结束位置
	double ScaleX, ScaleY;//刻度值
	public Axes(Context context) {
		super(context);
	}

	public Axes(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		Util.centerStartingX = centerStartingX = 0;
		Util.centerStartingY = centerStartingY = 0;
		Util.centerEndX = centerEndX = w;
		Util.centerEndY = centerEndY = h;
		Util.ScaleX = ScaleX = (double)(centerEndX - centerStartingX) / VAULE_X;
		Util.ScaleY = ScaleY = (double) (centerEndY - centerStartingY) / VALUE_Y;
		//Util.core = h;
		Util.spacingY = ScaleY;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setColor(Color.TRANSPARENT);

		if (canvas != null) {
			canvas.drawColor(Color.TRANSPARENT);
			canvas.drawLine(centerStartingX, centerEndY, centerEndX,
					centerEndY, paint);
			canvas.drawLine(centerStartingX, centerStartingY, centerStartingX,
					centerEndY, paint);
			int x = centerEndX, y = centerEndY;
			drawTriangle(canvas, new Point(x, y), new Point(x - 10, y - 5),
					new Point(x - 10, y + 5));
			canvas.drawText("X", x - 15, y + 18, paint);
			x = centerStartingX;
			y = centerStartingY;
			drawTriangle(canvas, new Point(x, y), new Point(x - 5, y + 10),
					new Point(x + 5, y + 10));
			canvas.drawText("Y", x + 12, y + 15, paint);
			x = centerStartingX;
			y = centerEndY;
			for (int i = 0; i < VAULE_X-1; i++) {
				x += ScaleX;
				drawScale(canvas, new Point(x, y), new Point(x, y - 20));
			}
			x = centerStartingX; 
			y = centerStartingY;
			for (int i = 0; i < VALUE_Y-1; i++) {
				y += ScaleY;
				drawScale(canvas, new Point(x, y), new Point(x + 20, y));
			}
		}
	}
	/**
	 * ������� ���ڻ������ļ�ͷ
	 */
	private void drawTriangle(Canvas canvas, Point p1, Point p2, Point p3) {
		Path path = new Path();
		path.moveTo(p1.x, p1.y);
		path.lineTo(p2.x, p2.y);
		path.lineTo(p3.x, p3.y);
		path.close();

		Paint paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPath(path, paint);
	}
	/**
	 * ������ ���ڻ������Ŀ̶�
	 */
	private void drawScale(Canvas canvas, Point p1, Point p2) {
		Paint paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		// ���ƿ̶�
		canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
	}
}
