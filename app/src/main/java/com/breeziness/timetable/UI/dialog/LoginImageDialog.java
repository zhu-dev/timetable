package com.breeziness.timetable.UI.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.editText.NumberInputView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;


/**
 * 用户登录输入验证码dialog
 * builder设计模式
 */
public class LoginImageDialog extends Dialog {
    private Context context;
    private OnDismissListener onDismissListener;//输入完成关闭对话框监听回调接口实例

    public LoginImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    //内部类
    public static class Builder {
        private LoginImageDialog mDialog;
        private NumberInputView inputView;//自定义输入框
        private Context context;
        private ImageView iv_login;

        public Builder(Context context, Bitmap bitmap) {
            this.context = context;
            mDialog = new LoginImageDialog(context, R.style.Theme_AppCompat_Dialog);//获取dialog的实例
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//获得加载器实例
            if (inflater != null) {
                //加载布局文件
                View view = inflater.inflate(R.layout.layout_dialog_login, null, false);
                //添加布局文件
                mDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                inputView = view.findViewById(R.id.input_image_number);
                iv_login = view.findViewById(R.id.iv_login);

                if (iv_login != null && bitmap != null) {
                    iv_login.setImageBitmap(bitmap);
                }

            }
            if (mDialog.getWindow() != null) {
                mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//解决圆角出现菱角的问题，实际上就是将默认的背景设置为透明
            }
        }


        public LoginImageDialog create() {

            //输入完成监听
            inputView.setOnInputCompleteListener(new NumberInputView.OnInputCompleteListener() {
                @Override
                public void onComplete(final String content) {
                    //输入完成后的操作
                    Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
                    mDialog.onDismissListener.onDismiss(content);
                    //延时200ms后关闭显示,并自动关闭软键盘
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(200);
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            });
            return mDialog;
        }

    }

    //重写onshow()方法，软键盘的显示需要控件绘制完成，所以需要等待一段时间，这里用定时器等待一段时间200ms
    @Override
    public void show() {
        super.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (manager != null) {
                    manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }, 200);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public interface OnDismissListener {
        void onDismiss(String content);
    }
}
