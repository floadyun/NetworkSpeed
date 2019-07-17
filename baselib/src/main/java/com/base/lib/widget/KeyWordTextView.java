package com.base.lib.widget;

/**
 * @copyright : 深圳市喜投金融服务有限公司
 * Created by yixf on 2018/11/27
 * @description:
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.base.lib.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description :
 * PackageName : com.mrtrying
 * Created by mrtrying on 2018/6/7 10:24.
 * e_mail : ztanzeyu@gmail.com
 */
public class KeyWordTextView extends AppCompatTextView {

    //原始文本
    private String mOriginalText = "";
    //关键字
    private String mSignText;
    //关键字颜色
    private int mSignTextColor;

    public KeyWordTextView(Context context) {
        super(context);
    }

    public KeyWordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttrs(attrs);
    }

    //初始化自定义属性
    private void initializeAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.KeyWordTextView);
        //获取关键字
        mSignText = typedArray.getString(R.styleable.KeyWordTextView_signText);
        //获取关键字颜色
        mSignTextColor = typedArray.getColor(R.styleable.KeyWordTextView_signTextColor, getTextColors().getDefaultColor());
        typedArray.recycle();
    }

    //重写setText方法
    @Override
    public void setText(CharSequence text, BufferType type) {
        this.mOriginalText = text.toString();
        super.setText(matcherSignText(), type);
    }

    /**
     * 匹配关键字，并返回SpannableStringBuilder对象
     * @return
     */
    private SpannableStringBuilder matcherSignText() {
        if (TextUtils.isEmpty(mOriginalText)) {
            return new SpannableStringBuilder("");
        }
        if (TextUtils.isEmpty(mSignText)) {
            return new SpannableStringBuilder(mOriginalText);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(mOriginalText);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mSignTextColor);
        Pattern p = Pattern.compile(mSignText);
        Matcher m = p.matcher(mOriginalText);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            builder.setSpan(foregroundColorSpan, start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    /**
     * 设置关键字
     * @param signText 关键字
     */
    public void setSignText(String signText) {
        mSignText = signText;
        setText(mOriginalText);
    }

    /**
     * 设置关键字颜色
     * @param signTextColor 关键字颜色
     */
    public void setSignTextColor(int signTextColor) {
        mSignTextColor = signTextColor;
        setText(mOriginalText);
    }
}
