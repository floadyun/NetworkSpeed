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
	/*
	 * ������
	 */
	int centerStartingX, centerStartingY; // ������
	int centerEndX, centerEndY; // ����յ�
	int ScaleX, ScaleY; // �̶ȼ��

	public Axes(Context context) {
		super(context);
	}

	public Axes(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * �ؼ��������֮������ʾ֮ǰ���������������� ��ʱ���Ի�ȡ��Ļ�Ĵ�С���õ��������㡢�յ�����ڵ㡣
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		Util.centerStartingX = centerStartingX = w / 20;
		Util.centerStartingY = centerStartingY = h / 22;
		Util.centerEndX = centerEndX = w / 20 * 19;
		Util.centerEndY = centerEndY = h / 22 * 21;
		Util.ScaleX = ScaleX = (centerEndX - centerStartingX) / 10;
		Util.ScaleY = ScaleY = (centerEndY - centerStartingY) / 10;
		Util.core = centerEndY / 2 + centerStartingY / 2;
		Util.spacingY = ScaleY / 25.00;
		
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);

		// �������
		if (canvas != null) {
			canvas.drawColor(Color.WHITE);
			// ��ֱ��
			canvas.drawLine(centerStartingX, centerEndY, centerEndX,
					centerEndY, paint);
			canvas.drawLine(centerStartingX, centerStartingY, centerStartingX,
					centerEndY, paint);
			// ��X���ͷ
			int x = centerEndX, y = centerEndY;
			drawTriangle(canvas, new Point(x, y), new Point(x - 10, y - 5),
					new Point(x - 10, y + 5));
			canvas.drawText("X", x - 15, y + 18, paint);
			// ��Y���ͷ
			x = centerStartingX;
			y = centerStartingY;
			drawTriangle(canvas, new Point(x, y), new Point(x - 5, y + 10),
					new Point(x + 5, y + 10));
			canvas.drawText("Y", x + 12, y + 15, paint);
			// ��X��̶�
			x = centerStartingX;
			y = centerEndY;
			for (int i = 0; i < 9; i++) {
				x += ScaleX;
				drawScale(canvas, new Point(x, y), new Point(x, y - 20));
			}
			// ��Y��̶�
			x = centerStartingX; 
			y = centerStartingY;
			for (int i = 0; i < 9; i++) {
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
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		// ������������
		canvas.drawPath(path, paint);
	}

	/**
	 * ������ ���ڻ������Ŀ̶�
	 */
	private void drawScale(Canvas canvas, Point p1, Point p2) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		// ���ƿ̶�
		canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
	}

}
