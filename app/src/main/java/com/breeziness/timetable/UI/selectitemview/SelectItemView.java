package com.breeziness.timetable.UI.selectitemview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class SelectItemView extends View {

    private Context mContext;
    private List<String> mDataList = new ArrayList<>();//显示的数据

    //关于字体
    private int mTextMaxWidth;//字体最大的宽度
    private int mTextMaxHeight;//字体最大的高度
    private int mSelectItemTextSize;//item字体设置的大小
    private int mTextSize;//字体大小

    //间隔空间
    private int mItemWidthSpace;//和文字水平之间的距离
    private int mItemHeightSpace;//每个item垂直的距离

    //文本内容
    private String mItemText;//内容

    //item 相关
    private int mFirstItemX;//第一个item的X坐标，所有的itemX坐标相等
    private int mFirstItemY;//第一个item的Y坐标
    private int mItemHeight;//item的高度
    private int mItemWidth;//item的宽度
    private int mMidVisibleItem;//处于中间位置的item,为了获得中间位置

    private int mLastDownY;//上次按下的Y坐标
    private int mScrollOffsetY;//滑动偏移
    private int perDaltaY;//每次滑动的差值
    private VelocityTracker mTracker;//速度跟踪对象
    private Scroller mScroller;//滑动对象
    private int mMaxVelocity;//最大的速度
    private int mMinVelocity;//最小的速度
    private int mScrollslop;
    private Handler mHandler = new Handler();

    //画笔相关
    private Paint mPaint;//画笔


    public SelectItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        //获得滑动辅助类实例
        mScroller = new Scroller(context);
        //获取滑动最大和最小的速度
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
        mScrollslop = configuration.getScaledTouchSlop();


        mPaint = new Paint(ANTI_ALIAS_FLAG);//抗锯齿

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = mTextMaxWidth + mItemWidthSpace + getPaddingLeft() + getPaddingRight();
        int height = (mTextMaxHeight + mItemHeightSpace)* getVisibleItemCount() + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureSize(specWidthMode, specWidthSize, width), measureSize(specHeightMode, specHeightSize, height));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int visibleItemCount = -mScrollOffsetY / mItemHeight;
        for (int itemDataPos = visibleItemCount - mMidVisibleItem - 1; itemDataPos <= visibleItemCount + mMidVisibleItem + 1; itemDataPos++) {
            if (itemDataPos < 0 || itemDataPos > mDataList.size() - 1) {
                continue;//超界结束掉这次循环
            }
            //计算Y坐标
            int itemDrawY = mFirstItemY + (itemDataPos + mMidVisibleItem) * mItemHeight + mScrollOffsetY;
            String data = mDataList.get(itemDataPos);
            canvas.drawText(data, mFirstItemX, itemDrawY, mPaint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                mTracker.clear();//清空
                mLastDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE://滑动
                perDaltaY = (int) (event.getY() - mLastDownY);//每次移动的偏移
                mScrollOffsetY += perDaltaY;//偏移总量
                mLastDownY = (int) event.getY();//更新上一次Y坐标
                invalidate();//刷新，重新绘制
                break;
            case MotionEvent.ACTION_UP://抬起
                mTracker.computeCurrentVelocity(1000, mMaxVelocity);
                int velocity_Y = (int) mTracker.getYVelocity();
                mScroller.fling(0, mScrollOffsetY, 0, velocity_Y, 0, 0, mMaxVelocity, mMinVelocity);
                mScroller.setFinalY(mScroller.getFinalY() + calcDiatanceToEnd(mScroller.getCurrY() % mItemHeight));
                mHandler.post(mScrollerRunnable);
                mTracker.recycle();
                mTracker = null;
                break;
        }
        return super.onTouchEvent(event);
    }


    private Runnable mScrollerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                int mScrollerCurY =mScroller.getCurrY();
                mScrollOffsetY = mScrollerCurY;
                postInvalidate();
                mHandler.postDelayed(this,16);
            }
        }
    };

    /**
     * 计算到最后的位置点的距离
     *
     * @param remainder
     * @return
     */
    private int calcDiatanceToEnd(int remainder) {
        if (Math.abs(remainder) > mItemHeight / 2) {
            if (mScrollOffsetY > 0) {
                return mItemHeight - remainder;
            } else {
                return -mItemHeight - remainder;
            }
        } else {
            return -remainder;
        }
    }

    /**
     * 实现wrap_content
     * 测量字体的大小，确保字体最大不会超过item的高度
     */
    private void calculateTextSize() {
        mTextMaxHeight = 0;
        mTextMaxWidth = 0;

        if (mDataList.size() == 0) {
            return;
        }

        mPaint.setTextSize(mSelectItemTextSize > mTextSize ? mSelectItemTextSize : mTextSize);

        if (!TextUtils.isEmpty(mItemText)) {
            mTextMaxWidth = (int) mPaint.measureText(mItemText);
        } else {
            mTextMaxWidth = (int) mPaint.measureText(mDataList.get(0));
        }

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mTextMaxHeight = (int) (fm.bottom - fm.top);
    }
    /**
     * 显示的个数等于上下两边Item的个数+ 中间的Item
     * @return 总显示的数量
     */
    public int getVisibleItemCount() {
        return mMidVisibleItem * 2 + 1;
    }
    /**
     * 获取适合的字体大小
     *
     * @param specMode
     * @param specSize
     * @param size
     * @return
     */
    private int measureSize(int specMode, int specSize, int size) {

        //判断mach_parenth、固定大小和wrap_content
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            return Math.min(specSize, size);
        }
    }
}
