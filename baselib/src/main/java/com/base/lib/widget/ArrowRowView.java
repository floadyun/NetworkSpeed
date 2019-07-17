/**
 * shopmobile for tpshop
 * ============================================================================
 * 版权所有 2015-2099 深圳搜豹网络科技有限公司，并保留所有权利。
 * 网站地址: http://www.tp-shop.cn
 * ——————————————————————————————————————
 * 这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和使用 .
 * 不允许对程序代码以任何形式任何目的的再发布。
 * ============================================================================
 * Author: 飞龙  wangqh01292@163.com
 * Date: @date 2015年11月14日 下午8:17:18 
 * Description:带箭头的自定义view
 * @version V1.0
 */
package com.base.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.lib.R;
import com.base.lib.autolayout.AutoFrameLayout;
import com.base.lib.util.DeviceUtils;

/**
 * @author 飞龙
 *
 */
public class ArrowRowView extends AutoFrameLayout {

	private TextView mTitleTxtv;
	private TextView mSubTitleTxtv;
	private ImageView imageImgv;
	private RelativeLayout arrowLayout;
	private View arrowImgv,pointView;
	private View subView;
	private View lineView;
	private boolean indicatorShow,lineShow,subImageShow,pointShow;
	private int lineStyle;
	/**
	 * @param context
	 * @param attrs
	 */
	public ArrowRowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.view_bg_selector);
		/** 获取自定义属性 titleText */
	    TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArrowView, 0, 0);
	    String titleText = typeArray.getString(R.styleable.ArrowView_titleText);
	    String subTitleText = typeArray.getString(R.styleable.ArrowView_subTitleText);

		Drawable drawable = typeArray.getDrawable(R.styleable.ArrowView_arrowSrc);
		indicatorShow = typeArray.getBoolean(R.styleable.ArrowView_indicatorShow, true);
		lineShow = typeArray.getBoolean(R.styleable.ArrowView_lineShow, true);
		subImageShow = typeArray.getBoolean(R.styleable.ArrowView_subImageShow,false);
		lineStyle = typeArray.getInteger(R.styleable.ArrowView_lineStyle,0);
		pointShow = typeArray.getBoolean(R.styleable.ArrowView_pointShow, false);

	    View view = LayoutInflater.from(context).inflate(R.layout.right_arrow_row_view, this);

	    mTitleTxtv = view.findViewById(R.id.title_txtv);
	    mTitleTxtv.setText(titleText);
		mTitleTxtv.setTextSize(TypedValue.COMPLEX_UNIT_PX,typeArray.getDimension(R.styleable.ArrowView_titleTextSize, DeviceUtils.getDeviceDimen(context,28)));
		mTitleTxtv.setTextColor(typeArray.getColor(R.styleable.ArrowView_titleTextColor, getResources().getColor(R.color.normal_black_text_color)));

		mSubTitleTxtv = view.findViewById(R.id.sub_title_text);
		mSubTitleTxtv.setText(subTitleText);
		mSubTitleTxtv.setTextSize(TypedValue.COMPLEX_UNIT_PX,typeArray.getDimension(R.styleable.ArrowView_subTextSize, DeviceUtils.getDeviceDimen(context,24)));
		mSubTitleTxtv.setHint(typeArray.getString(R.styleable.ArrowView_subHintText));
		mSubTitleTxtv.setTextColor(typeArray.getColor(R.styleable.ArrowView_subTextColor, getResources().getColor(R.color.normal_gray_text_color)));

		imageImgv = view.findViewById(R.id.image_imgv);
		arrowLayout = view.findViewById(R.id.arrow_layout);
		arrowImgv = view.findViewById(R.id.arrow_imgv);
		subView = view.findViewById(R.id.item_sub_image);
		lineView = view.findViewById(R.id.row_line_view);
		pointView = view.findViewById(R.id.red_point_view);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		float marginLeft = 0 ;

		if (drawable != null){
		    imageImgv.setVisibility(VISIBLE);
			marginLeft = DeviceUtils.getDeviceDimen(getContext(),20);
			params.addRule(RelativeLayout.RIGHT_OF,R.id.image_imgv);
			params.addRule(RelativeLayout.CENTER_VERTICAL);
			params.setMargins(Float.valueOf(marginLeft).intValue(), 0, 0, 0);
			imageImgv.setImageDrawable(drawable);
			mTitleTxtv.setLayoutParams(params);
		}else{
			marginLeft = context.getResources().getDimension(R.dimen.normal_margin_space);
			params.setMargins(Float.valueOf(marginLeft).intValue() , 0 , 0 , 0);
			imageImgv.setVisibility(GONE);
		}
		if (indicatorShow){
			arrowLayout.setVisibility(View.VISIBLE);
		}else {
			arrowLayout.setVisibility(View.INVISIBLE);
		}
		if(subImageShow){
			subView.setVisibility(VISIBLE);
		}
		mTitleTxtv.setText(titleText);
		if(lineShow){
			lineView.setVisibility(View.VISIBLE);
			LinearLayout.LayoutParams lineParams = (LinearLayout.LayoutParams) lineView.getLayoutParams();
			switch (lineStyle){
				case 1:
					lineParams.leftMargin = DeviceUtils.getDeviceDimen(context,30);
					break;
				case 2:
					lineParams.rightMargin = DeviceUtils.getDeviceDimen(context,30);
					break;
				case 3:
					lineParams.leftMargin = DeviceUtils.getDeviceDimen(context,30);
					lineParams.rightMargin = DeviceUtils.getDeviceDimen(context,30);
					break;
			}
			lineView.setLayoutParams(lineParams);
		}else{
			lineView.setVisibility(View.INVISIBLE);
		}
		if(pointShow){
			pointView.setVisibility(VISIBLE);
		}
	}

	public void setIndicatorShow(boolean show){
		indicatorShow = show;
		if (indicatorShow){
			arrowImgv.setVisibility(View.VISIBLE);
		}else {
			arrowImgv.setVisibility(View.INVISIBLE);
		}
	}
	/**
	 * 设置红点是否显示
	 * @param show
	 */
	public void setPointShow(boolean show){
		if(show){
			pointView.setVisibility(VISIBLE);
		}else {
			pointView.setVisibility(GONE);
		}
	}
	public void setText(String text){
		if(mTitleTxtv == null || text == null)return;
		mTitleTxtv.setText(text);
	}

	public void setSubText(String text){
		if(mSubTitleTxtv == null || text == null)return;
		mSubTitleTxtv.setText(text);
	}
	public void setSubImageVisibility(int visibility){
		subView.setVisibility(visibility);
	}
	/**
	 * 获取子标题
	 * @return
	 */
	public String getSubText(){
		return mSubTitleTxtv.getText().toString();
	}
}





















































































