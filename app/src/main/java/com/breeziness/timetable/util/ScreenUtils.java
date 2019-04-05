package com.breeziness.timetable.util;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;


public class ScreenUtils {




    /**
     * 获取屏幕的宽度
     *
     * @return ScreenWidth(px)
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//获取windowManager
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;//获取以px表示的宽度
        }
        return 1080;//默认返回1080px
    }

    /**
     * 返回屏幕的高度
     *
     * @return
     */
    public  static int getScreenHeitht(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//获取windowManager
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels;//获取以px表示的高度
        }
        return 600;//默认返回600px
    }

    /**
     * dip转化为px
     *
     * @param dip  dp
     * @return px
     */
    public  static int dip2px(Context context,float dip) {
        float scale = context.getResources().getDisplayMetrics().density;//获取屏幕密度，相对密度，近似
        return (int) (dip * scale + 0.5f);//转化并四舍五入
    }


    /**
     * 获取底部导航栏高度
     * @param context  context
     * @return 导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        if (checkDeviceHasNavigationBar(context)) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            //获取NavigationBar的高度
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    private static boolean checkDeviceHasNavigationBar(Context context) {

        //通过判断设备是否有物理按键
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        return !hasMenuKey && !hasBackKey;
    }
}
