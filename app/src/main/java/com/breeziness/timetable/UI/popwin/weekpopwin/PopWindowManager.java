package com.breeziness.timetable.UI.popwin.weekpopwin;

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


    private void init() {
        //设置内容、宽度、高度、
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //动画效果
        //popupWindow.setAnimationStyle();
        //弹出菜单背景色
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);//透明色 transparent
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setOutsideTouchable(true);//是否响应window以外的触摸事件，可以通过点击外部空白处退出
        //弹出键盘后显示的问题
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//总是隐藏软键盘
        //popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//当显示软键盘时，调整window内的控件大小以便显示软键盘。这样的话控件可能会变形。



        //设置关闭监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissListener != null) {

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
     * 设置关闭监听的方法
     *
     * @param onDismissListener
     */
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }



    /**
     * 视图消失回调接口
     * 可以在这里实现背景透明度恢复
     */
    public interface OnDismissListener {
        void OnDismiss();
    }
}
