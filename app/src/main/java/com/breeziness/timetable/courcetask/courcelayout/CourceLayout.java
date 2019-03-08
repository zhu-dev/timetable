package com.breeziness.timetable.courcetask.courcelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.breeziness.timetable.R;
import com.breeziness.timetable.courcetask.courceview.CourceView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 课程容器布局
 * 通过获得屏幕宽度，均分7份
 */
public class CourceLayout extends ViewGroup {
    private static final String TAG = "CourceLayout";
    private List<CourceView> courceViews = new ArrayList<CourceView>();//课程控件的集合

    private int layoutWidth; //布局的宽度
    private int layoutHeight; //布局的高度
    private int sectionHeight; //课程的高度
    private int sectionWidth; //课程的宽度
    private int sectionTotal = 6; //一天的节数
    private int weekdayTotal = 7; //一周的天数
    private int divideWidth;//间隔线宽度 默认2dp
    private int divideHeight;//间隔线宽度 默认2dp


    public CourceLayout(Context context) {
        this(context, null);
    }

    public CourceLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourceLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutHeight = dip2px(690);//默认高度600dp
        layoutWidth = getScreenWidth() - (int) getContext().getResources().getDimension(R.dimen.weekview_month_width);//默认宽度为屏幕宽度，需要考虑下左侧的课程节数栏
        divideWidth = dip2px(3);//默认间隔3dp
        divideHeight = dip2px(3);//默认间隔3dp

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(layoutWidth, layoutHeight);//保存测量的宽高
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        courceViews.clear();//清空课程控件容器

        sectionHeight = (getMeasuredHeight() - divideHeight * sectionTotal) / sectionTotal;//每节课的高度
        sectionWidth = (getMeasuredWidth() - divideWidth * weekdayTotal) / weekdayTotal;//每节课的宽度

        Log.e(TAG, "getMeasuredHeight()----> " + getMeasuredHeight());
        int childCount = getChildCount();//获取子控件的个数。
        for (int i = 0; i < childCount; i++) {
            CourceView child = (CourceView) getChildAt(i);
            courceViews.add(child);//添加这个课程到list中


            //获得影响位置的三个属性
            int startSection = child.getStartSection();
            int endSection = child.getEndSection();
            int weekday = child.getWeekday();

            //计算坐标
            int left = (weekday - 1) * sectionWidth + weekday * divideWidth;//左边的坐标，默认week是从1开始
            int right = left + sectionWidth;//计算右边的坐标
            int top = (startSection - 1) * sectionHeight + startSection * divideHeight;//顶部坐标
            int bottom = (endSection - startSection + 1) * sectionHeight + top + (endSection - startSection) * divideHeight;//底部坐标
            child.layout(left, top, right, bottom);
        }

    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);//获取windowManager
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
    public int getScreenHeitht() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);//获取windowManager
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
     * @param dip
     * @return
     */
    public int dip2px(float dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;//获取屏幕密度，相对密度，近似
        Log.e(TAG, "dip2px:scale------> " + scale);
        return (int) (dip * scale + 0.5f);//转化并四舍五入
    }
}
