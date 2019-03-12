package com.breeziness.timetable.coursetask.popwin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopWindowManager {
    private Context context;
    private View contentView;//内容视图
    private View anchor;//弹出位置锚点视图
    private PopupWindow popupWindow;
    private OnDismissListener onDismissListener;//视图消失回调监听

    public PopWindowManager(Context context, View contentView, View anchor) {
        this.context = context;
        this.contentView = contentView;
        this.anchor = anchor;
        init();
    }

    @SuppressLint("WrongConstant")
    private void init() {
        //设置内容、宽度、高度、
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //动画效果
        //popupWindow.setAnimationStyle();
        //弹出菜单背景色
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);//透明色 transparent
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setOutsideTouchable(true);//是否响应window以外的触摸事件
        //关闭事件
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissListener != null){
                    onDismissListener.OnDismiss();
                }

            }
        });

    }

    /**
     * 显示popwindow
     */
    public void show() {
        popupWindow.showAsDropDown(anchor);
    }

    /**
     * 隐藏popwindow
     */
    public void hide() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 是否在显示的判断
     *
     * @return
     */
    public boolean isShow() {
        return popupWindow.isShowing();
    }

    /**
     * 设置监听的方法
     *
     * @param onDismissListener
     */
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    /**
     * 视图消失回调接口
     */
    public interface OnDismissListener {
        void OnDismiss();
    }
}
