package com.base.lib.xrichtext;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @copyright : 深圳市喜投金融服务有限公司
 * Created by yixf on 2018/7/30
 * @description:自定义文字点击事件
 */
public abstract class CustomClickableSpan extends ClickableSpan {

    public abstract void onTextCLick(View view);

    private String content;

    private int color;

    private int start,end;

    public CustomClickableSpan(String content,int color) {
        this.content = content;
        this.color = color;
    }
    public void setStartAndEnd(int start,int end){
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
    @Override
    public void updateDrawState(TextPaint ds) { //设置显示样式
        ds.setUnderlineText(false);  //不要默认下划线
        ds.setColor(color);
    }
    @Override
    public void onClick(View widget) { //点击事件的响应方法
        onTextCLick(widget);
        return;
    }
}