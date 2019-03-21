package com.breeziness.timetable.UI.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breeziness.timetable.R;

import androidx.annotation.NonNull;

public class LoadingDialog extends Dialog {

    private Context context;

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    //内部类
    public static class Builder {

        private Context context;
        LoadingDialog mDialog;
        public Builder(Context context) {
            this.context = context;
            mDialog = new LoadingDialog(context, R.style.CusTheme_Dialog_Theme_Dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//获得加载器实例
            if (inflater != null) {
                //加载布局文件
                View view = inflater.inflate(R.layout.layout_dialog_loading, null, false);
                //添加布局文件
                mDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            //不可点击外部空白区取消
            mDialog.setCanceledOnTouchOutside(false);

            //解决圆角出现菱角的问题，实际上就是将默认的背景设置为透明
            if (mDialog.getWindow() != null) {
                mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
               // mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
               // mDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            }

        }

        public LoadingDialog create() {
            return mDialog;
        }

    }
}
