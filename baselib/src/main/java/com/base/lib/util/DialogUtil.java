package com.base.lib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.base.lib.widget.CustomDialog;

/**
 * Created by yixiaofei on 2017/3/17 0017.
 */

public class DialogUtil {
    public interface OnDialogClickEvent{
        void positiveClickEvent(String editText);
        void cancelClickEvent();
    }
    public static final int NO_ICON = -1;  //无图标

    public static CustomDialog.Builder builder;

    /**
     * 创建消息对话框
     *
     * @param context 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title 标题 必填
     * @param message 显示内容 必填
     * @param btnName 按钮名称 必填
     * @param listener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createMessageDialog(Context context, String title, String message,
                                             String btnName, DialogInterface.OnClickListener listener, int iconId)
    {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON)
        {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置对话框消息
        builder.setMessage(message);
        //设置按钮
        builder.setPositiveButton(btnName, listener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }
    /**
     * 显示提示框
     * @param message
     * @param suerText
     * @param cancelText
     */
    public static void showNotifDialog(Activity activity, String title,String message, String suerText, String cancelText, final OnDialogClickEvent dialogClickEvent){
        if(builder==null){
            builder = new CustomDialog.Builder(activity);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(suerText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(dialogClickEvent!=null){
                        dialogClickEvent.positiveClickEvent("");
                    }
                    dialog.dismiss();
                    builder = null;
                }
            });
            if(!cancelText.equals("")){
                builder.setNegativeButton(cancelText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(dialogClickEvent!=null){
                                    dialogClickEvent.cancelClickEvent();
                                }
                                dialog.dismiss();
                                builder = null;
                            }
                        });
            }
            builder.create().show();
        }
    }
    /**
     * 创建警示（确认、取消）对话框
     *
     * @param context 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title 标题 必填
     * @param message 显示内容 必填
     * @param positiveBtnName 确定按钮名称 必填
     * @param negativeBtnName 取消按钮名称 必填
     * @param positiveBtnListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param negativeBtnListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createConfirmDialog(Context context, String title, String message,
                                             String positiveBtnName, String negativeBtnName, DialogInterface.OnClickListener positiveBtnListener,
                                             DialogInterface.OnClickListener negativeBtnListener, int iconId)
    {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON)
        {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置对话框消息
        builder.setMessage(message);
        //设置确定按钮
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        //设置取消按钮
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        //创建一个消息对话框
        dialog = builder.create();
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * 创建单选对话框
     *
     * @param context 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title 标题 必填
     * @param itemsString 选择项 必填
     * @param positiveBtnName 确定按钮名称 必填
     * @param negativeBtnName 取消按钮名称 必填
     * @param positiveBtnListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param negativeBtnListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param itemClickListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createSingleChoiceDialog(Context context, String title, String[] itemsString,
                                                  String positiveBtnName, String negativeBtnName, DialogInterface.OnClickListener positiveBtnListener,
                                                  DialogInterface.OnClickListener negativeBtnListener, DialogInterface.OnClickListener itemClickListener, int iconId)
    {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON)
        {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置单选选项, 参数0: 默认第一个单选按钮被选中
        builder.setSingleChoiceItems(itemsString, 0, itemClickListener);
        //设置确定按钮
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        //设置确定按钮
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }

    /**
     * 创建复选对话框
     *
     * @param context 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title 标题 必填
     * @param itemsString 选择项 必填
     * @param positiveBtnName 确定按钮名称 必填
     * @param negativeBtnName 取消按钮名称 必填
     * @param positiveBtnListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param negativeBtnListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param itemClickListener 监听器，需实现android.content.DialogInterface.OnMultiChoiceClickListener;接口 必填
     * @return
     */
    public static Dialog createMultiChoiceDialog(Context context, String title, String[] itemsString,
                                                 String positiveBtnName, String negativeBtnName, DialogInterface.OnClickListener positiveBtnListener,
                                                 DialogInterface.OnClickListener negativeBtnListener, DialogInterface.OnMultiChoiceClickListener itemClickListener, int iconId)
    {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON)
        {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置选项
        builder.setMultiChoiceItems(itemsString, null, itemClickListener);
        //设置确定按钮
        builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        //设置确定按钮
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }
    /**
     * 创建列表对话框
     *
     * @param context 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
     * @param title 标题 必填
     * @param itemsString 列表项 必填
     * @param negativeBtnName 取消按钮名称 必填
     * @param negativeBtnListener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createListDialog(Context context, String title, String[] itemsString,
                                          String negativeBtnName, DialogInterface.OnClickListener negativeBtnListener,
                                          DialogInterface.OnClickListener itemClickListener, int iconId)
    {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (iconId != NO_ICON)
        {
            //设置对话框图标
            builder.setIcon(iconId);
        }
        //设置对话框标题
        builder.setTitle(title);
        //设置列表选项
        builder.setItems(itemsString, itemClickListener);
        //设置确定按钮
        builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        //创建一个消息对话框
        dialog = builder.create();

        return dialog;
    }
    /**
     * 创建编辑对话框
     * @param context
     * @param hintText
     * @param sureText
     * @return
     */
    public static void createEditDialog(Context context,String titleText,String hintText,String sureText,String cancelText,final OnDialogClickEvent dialogClickEvent){
        if(builder==null){
            builder = new CustomDialog.Builder(context);
            final EditText editText = new EditText(context);
            editText.setHint(hintText);
            editText.setBackgroundResource(0);
            editText.setGravity(Gravity.CENTER);
            editText.setFilters(new  InputFilter[]{ new  InputFilter.LengthFilter(9)});
            builder.setTitle(titleText).setContentView(editText);
            builder.setPositiveButton(sureText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    builder = null;
                    if(dialogClickEvent!=null){
                        dialogClickEvent.positiveClickEvent(editText.getText().toString());
                    }
                }
            });
            if(!cancelText.equals("")){
                builder.setNegativeButton(cancelText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                builder = null;
                                if(dialogClickEvent!=null){
                                    dialogClickEvent.cancelClickEvent();
                                }
                            }
                        });
            }
        }
        builder.create().show();
    }
    /**
     * 创建自定义View对话框
     * @param view
     */
    public static Dialog createViewDialog(Context context,View view){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        return dialog;
    }
}
