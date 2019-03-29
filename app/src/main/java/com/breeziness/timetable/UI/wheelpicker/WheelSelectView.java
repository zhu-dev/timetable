package com.breeziness.timetable.UI.wheelpicker;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.breeziness.timetable.R;
import com.breeziness.timetable.util.LinearGradientUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class WheelSelectView<T> extends View {

    private static final String TAG = "WheelSelectView";
    private Context mContext;

    private List<T> mDataList = new ArrayList<>();//显示的数据
    private OnSelectedChangeListener onSelectedChangeListener;//数据选择监听回调接口实例
    //关于字体
    private int mTextMaxWidth;//字体最大的宽度
    private int mTextMaxHeight;//字体最大的高度
    private int mSelectItemTextSize = 12;//选中的item字体设置的大小
    private int mSelectItemTextColor;//选中的item字体设置的颜色
    private int mTextSize = 10;//字体大小
    private int mTextColor;//字体颜色

    //间隔空间
    private int mItemWidthSpace = 2;//和文字水平之间的距离
    private int mItemHeightSpace = 5;//每个item垂直的距离

    //文本内容
    private String mItemText;//内容

    //item 相关
    private int mFirstItemX;//第一个item的X坐标，所有的itemX坐标相等
    private int mFirstItemY;//第一个item的Y坐标
    private int mCenterItemDrawnY;//中间的item的Y坐标
    private int mItemHeight;//item的高度
    private int mItemWidth;//item的宽度
    private int mMidVisibleItemCount = 2;//处于中间位置的item,为了使得选择的item位于中间位置


    //容器区域
    private final Rect mDrawnRect;//总空间
    private final Rect mSelectedItemRect;//选择的item空间

    //fling 相关
    private int mCurrentPosition = 5; //当前坐标
    private int mLastDownY;//上次按下的Y坐标
    private int mScrollOffsetY;//滑动偏移
    private VelocityTracker mTracker;//速度跟踪对象
    private Scroller mScroller;//滑动对象
    private int mMaxVelocity;//最大的速度
    private int mMinVelocity;//最小的速度
    private int mMinFlingY;//最小的fling
    private int mMaxFlingY;//最大的fling
    private int mScrollslop;
    private boolean mIsAbortScroller;//是否手动停止滑动
    private int mTouchDownY;
    private boolean mTouchSlopFlag;
    private int mMaximumVelocity = 12000;
    private int mMinimumVelocity = 50;

    //附加效果
    private boolean mIsCyclic = false;//是否开启循环模式
    private boolean mIsTextGradual;//是否开启字体大小渐变
    private boolean mIsEnlargeSelectedItem;//是否放大选择项
    private boolean mIsShowSelectedCurtain;//是否显示选择项的底色（幕布）
    private boolean mIsShowCurtainBorder;//是否绘制边框
    private int mSelectedItemCurtainColor;//幕布的颜色
    private int mSelectedItemCurtainBorder;//幕布的边框
    private int mSelectedItemCurtainBorderColor;//幕布的边框的颜色

    //线性颜色渐变
    private LinearGradientUtil mLinearGradient;

    //画笔相关
    private Paint mPaint;//画笔
    private Paint mTextPaint;//文字画笔
    private Paint mSelectedItemPaint;//选中item的画笔

    //handler
    private Handler mHandler = new Handler();

    private Runnable mScrollerRunnable = new Runnable() {
        @Override
        public void run() {

            if (mScroller.computeScrollOffset()) {
                mScrollOffsetY = mScroller.getCurrY();
                postInvalidate();
                mHandler.postDelayed(this, 16);
            }
            if (mScroller.isFinished() || (mScroller.getCurrY() == mScroller.getFinalY() && mScroller.getCurrX() == mScroller.getFinalX())) {
                //如果item的高度为0，直接返回
                if (mItemHeight == 0) {
                    return;
                }

                int position = -mScrollOffsetY / mItemHeight;//获取显示的数量，获取位置信息
                position = fixItemPosition(position);//矫正位置
                //将选择的item内容和位置回调
                if (mCurrentPosition != position) {
                    mCurrentPosition = position;
                    if (onSelectedChangeListener == null) {
                        return;
                    }
                    onSelectedChangeListener.OnSelectedChange(mDataList.get(position).toString(), position);
                }
            }
        }
    };


    public WheelSelectView(Context context) {
        this(context, null);
    }

    public WheelSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WheelSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        //自定义线性渲染 实现字体颜色渐变
        mLinearGradient = new LinearGradientUtil(mTextColor, mSelectItemTextColor);

        //获得滑动辅助类实例
        mScroller = new Scroller(context);
        //获取滑动最大和最小的速度
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
        mScrollslop = configuration.getScaledTouchSlop();

        //绘制矩形
        mDrawnRect = new Rect();//总绘制区域
        mSelectedItemRect = new Rect();//中间选中的item的区域

        //初始化属性，获得自定义的属性
        initAttrs(context, attrs);

        //初始化画笔
        initPaints();
    }

    //初始化属性，获得自定义的属性
    private void initAttrs(Context context, @Nullable AttributeSet attrs) {

        //将默认属性值转化为px
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mSelectItemTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mSelectItemTextSize, dm);
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);

        //获得自定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WheelSelectView);
        mTextSize = array.getDimensionPixelSize(R.styleable.WheelSelectView_mTextSize, mTextSize);
        mTextColor = array.getColor(R.styleable.WheelSelectView_mTextColor, mTextColor);

        mItemHeightSpace = array.getDimensionPixelSize(R.styleable.WheelSelectView_mItemHeightSpace, mItemHeightSpace);
        mItemWidthSpace = array.getDimensionPixelSize(R.styleable.WheelSelectView_mItemWidthSpace, mItemWidthSpace);

        mSelectItemTextColor = array.getColor(R.styleable.WheelSelectView_mSelectItemTextColor, mSelectItemTextColor);
        mSelectItemTextSize = array.getDimensionPixelSize(R.styleable.WheelSelectView_mSelectItemTextSize, mSelectItemTextSize);

        mIsCyclic = array.getBoolean(R.styleable.WheelSelectView_mIsCyclic, false);
        mIsEnlargeSelectedItem = array.getBoolean(R.styleable.WheelSelectView_mIsEnlargeSelectedItem, true);
        mIsShowSelectedCurtain = array.getBoolean(R.styleable.WheelSelectView_mIsShowSelectedCurtain, true);
        mIsTextGradual = array.getBoolean(R.styleable.WheelSelectView_mIsTextGradual, true);
        mIsShowCurtainBorder = array.getBoolean(R.styleable.WheelSelectView_mIsShowCurtainBorder, true);

        mSelectedItemCurtainColor = array.getColor(R.styleable.WheelSelectView_mSelectedItemCurtainColor, mSelectedItemCurtainColor);
        mSelectedItemCurtainBorder = array.getDimensionPixelSize(R.styleable.WheelSelectView_mSelectedItemCurtainBorder, mSelectedItemCurtainBorder);
        mSelectedItemCurtainBorderColor = array.getColor(R.styleable.WheelSelectView_mSelectedItemCurtainBorderColor, mSelectedItemCurtainBorderColor);
        array.recycle();
    }

    //初始化画笔
    private void initPaints() {

        //主画笔，画区域
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        //文字画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        //选中的item的画笔
        mSelectedItemPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mSelectedItemPaint.setStyle(Paint.Style.FILL);
        mSelectedItemPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedItemPaint.setColor(mSelectItemTextColor);
        mSelectedItemPaint.setTextSize(mSelectItemTextSize);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = mTextMaxWidth + mItemWidthSpace + getPaddingLeft() + getPaddingRight();
        int height = (mTextMaxHeight + mItemHeightSpace) * getVisibleItemCount() + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureSize(specWidthMode, specWidthSize, width), measureSize(specHeightMode, specHeightSize, height));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextAlign(Paint.Align.CENTER);
        //是否绘制幕布
        if (mIsShowSelectedCurtain) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mSelectedItemCurtainColor);
            canvas.drawRect(mSelectedItemRect, mPaint);
        }
        //是否绘制边框
        if (mIsShowCurtainBorder) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mSelectedItemCurtainBorderColor);
            canvas.drawRect(mSelectedItemRect, mPaint);
            //canvas.drawRect(mDrawnRect, mPaint);//背景
        }

        int visibleItemCount = -mScrollOffsetY / mItemHeight;//可视的item的个数

        //上下各绘制一个作为缓冲
        for (int itemDataPos = visibleItemCount - mMidVisibleItemCount - 1; itemDataPos <= visibleItemCount + mMidVisibleItemCount + 1; itemDataPos++) {
            int position = itemDataPos;
            if (mIsCyclic) {
                position = fixItemPosition(position);
            } else {
                if (itemDataPos < 0 || itemDataPos > mDataList.size() - 1) {
                    continue;//超界结束掉这次循环
                }
            }
            //拿出数据
            T data = mDataList.get(position);

            //计算中间文本的Y坐标
            int itemDrawY = mFirstItemY + (itemDataPos + mMidVisibleItemCount) * mItemHeight + mScrollOffsetY;

            //目标item距离中间item的距离，绝对值
            int distanceY = Math.abs(mCenterItemDrawnY - itemDrawY);


            if (mIsTextGradual) {
                //文字颜色渐变要在设置透明度上边，否则会被覆盖
                //计算文字颜色渐变
                if (distanceY < mItemHeight) {  //距离中心的高度小于一个ItemHeight才会开启渐变
                    float colorRatio = 1 - (distanceY / (float) mItemHeight);//计算颜色渐变比值
                    //mSelectedItemPaint.setColor(mLinearGradient.getColor(colorRatio));//选择的item和其他item同色渐变
                    mTextPaint.setColor(mLinearGradient.getColor(colorRatio));
                } else {
                    mSelectedItemPaint.setColor(mSelectItemTextColor);
                    mTextPaint.setColor(mTextColor);
                }
                //计算透明度渐变
                float alphaRatio;//透明度渐变比值
                if (itemDrawY > mCenterItemDrawnY) {
                    alphaRatio = (mDrawnRect.height() - itemDrawY) / (float) (mDrawnRect.height() - (mCenterItemDrawnY));//下方的item透明度比值计算
                } else {
                    alphaRatio = itemDrawY / (float) mCenterItemDrawnY;//上方的item透明度计算
                }

                alphaRatio = alphaRatio < 0 ? 0 : alphaRatio;
                mSelectedItemPaint.setAlpha((int) (alphaRatio * 255));
                mTextPaint.setAlpha((int) (alphaRatio * 255));
            }
            //是否将中间的item放大
            if (mIsEnlargeSelectedItem) {
                //在一个item和item/2的范围内  加大addedSize
                if (distanceY < mItemHeight) {
                    float addedSize = (mItemHeight - distanceY) / (float) mItemHeight * (mSelectItemTextSize - mTextSize);
                    mSelectedItemPaint.setTextSize(mTextSize + addedSize);
                    mTextPaint.setTextSize(mTextSize + addedSize);
                } else {
                    mSelectedItemPaint.setTextSize(mTextSize);
                    mTextPaint.setTextSize(mTextSize);
                }
            } else {
                mSelectedItemPaint.setTextSize(mTextSize);
                mTextPaint.setTextSize(mTextSize);
            }
            //在一个0和item/2的范围内，使用mSelectedItemPaint画笔绘制，mSelectedItemPaint会比mTextPaint大
            if (distanceY < mItemHeight / 2) {
                canvas.drawText(data.toString(), mFirstItemX, itemDrawY, mSelectedItemPaint);
            } else {
                canvas.drawText(data.toString(), mFirstItemX, itemDrawY, mTextPaint);
            }

        }
    }

    /**
     * 当控件的宽高发生改变时，会调用这个方法
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawnRect.set(getPaddingLeft(), getPaddingTop(),
                getWidth() - getPaddingRight(), getHeight()-getPaddingBottom());
        mItemHeight = mDrawnRect.height() / getVisibleItemCount();
        mFirstItemX = mDrawnRect.centerX();
        mFirstItemY = (int) ((mItemHeight - (mSelectedItemPaint.ascent() + mSelectedItemPaint.descent())) / 2);
        // Log.e(TAG, "onSizeChanged: ---mFirstItemY----" + mFirstItemY);
        //中间的Item边框
        mSelectedItemRect.set(getPaddingLeft(), mItemHeight * mMidVisibleItemCount,
                getWidth() - getPaddingRight(), mItemHeight + mItemHeight * mMidVisibleItemCount);
        computeFlingLimitY();
        mCenterItemDrawnY = mFirstItemY + mItemHeight * mMidVisibleItemCount;
        mScrollOffsetY = -mItemHeight * mCurrentPosition;
        //Log.e(TAG, "onSizeChanged: ----mScrollOffsetY--" + mScrollOffsetY);
    }

    /**
     * 手势操作处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    mIsAbortScroller = true;
                } else {
                    mIsAbortScroller = false;
                }
                mTracker.clear();
                mTouchDownY = mLastDownY = (int) event.getY();
                mTouchSlopFlag = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchSlopFlag && Math.abs(mTouchDownY - event.getY()) < mScrollslop) {
                    break;
                }
                mTouchSlopFlag = false;
                float move = event.getY() - mLastDownY;
                mScrollOffsetY += move;
                mLastDownY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (!mIsAbortScroller && mTouchDownY == mLastDownY) {
                    performClick();
                    if (event.getY() > mSelectedItemRect.bottom) {
                        int scrollItem = (int) (event.getY() - mSelectedItemRect.bottom) / mItemHeight + 1;
                        mScroller.startScroll(0, mScrollOffsetY, 0,
                                -scrollItem * mItemHeight);

                    } else if (event.getY() < mSelectedItemRect.top) {
                        int scrollItem = (int) (mSelectedItemRect.top - event.getY()) / mItemHeight + 1;
                        mScroller.startScroll(0, mScrollOffsetY, 0,
                                scrollItem * mItemHeight);
                    }
                } else {
                    mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int velocity = (int) mTracker.getYVelocity();
                    if (Math.abs(velocity) > mMinimumVelocity) {
                        mScroller.fling(0, mScrollOffsetY, 0, velocity,
                                0, 0, mMinFlingY, mMaxFlingY);
                        mScroller.setFinalY(mScroller.getFinalY() +
                                computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
                    } else {
                        mScroller.startScroll(0, mScrollOffsetY, 0,
                                computeDistanceToEndPoint(mScrollOffsetY % mItemHeight));
                    }
                }
                if (!mIsCyclic) {
                    if (mScroller.getFinalY() > mMaxFlingY) {
                        mScroller.setFinalY(mMaxFlingY);
                    } else if (mScroller.getFinalY() < mMinFlingY) {
                        mScroller.setFinalY(mMinFlingY);
                    }
                }
                mHandler.post(mScrollerRunnable);
                mTracker.recycle();
                mTracker = null;
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    /**
     * 修正item的位置,使得每滑动一个位置都是item的高度的整数倍，避免item停在随缘的位置
     *
     * @param position
     * @return
     */
    private int fixItemPosition(int position) {
        if (position < 0) {
            //将数据集限定在0 ~ mDataList.size()-1之间
            position = mDataList.size() + (position % mDataList.size());

        }
        if (position >= mDataList.size()) {
            //将数据集限定在0 ~ mDataList.size()-1之间
            position = position % mDataList.size();
        }
        return position;
    }


    /**
     * 计算到最后的位置点的距离
     *
     * @param remainder
     * @return
     */
    private int computeDistanceToEndPoint(int remainder) {
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
    private void computeTextSize() {
        mTextMaxHeight = 0;
        mTextMaxWidth = 0;

        if (mDataList.size() == 0) {
            return;
        }

        mTextPaint.setTextSize(mSelectItemTextSize > mTextSize ? mSelectItemTextSize : mTextSize);

        if (!TextUtils.isEmpty(mItemText)) {
            mTextMaxWidth = (int) mTextPaint.measureText(mItemText);
        } else {
            mTextMaxWidth = (int) mTextPaint.measureText(mDataList.get(0).toString());
        }

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextMaxHeight = (int) (fm.bottom - fm.top);
    }

    /**
     * 显示的个数等于上下两边Item的个数+ 中间的Item
     *
     * @return 总显示的数量
     */
    public int getVisibleItemCount() {
        return mMidVisibleItemCount * 2 + 1;
    }

    /**
     * 计算Fling极限
     * 如果为Cyclic模式则为Integer的极限值，如果正常模式，则为一整个数据集的上下限。
     */
    private void computeFlingLimitY() {
        mMinFlingY = mIsCyclic ? Integer.MIN_VALUE :
                -mItemHeight * (mDataList.size() - 1);
        mMaxFlingY = mIsCyclic ? Integer.MAX_VALUE : 0;
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

        //判断mach_parent、固定大小和wrap_content
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            return Math.min(specSize, size);
        }
    }


    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(@NonNull List<T> dataList) {
        mDataList = dataList;
        if (dataList.size() == 0) {
            return;
        }
        computeTextSize();
        computeFlingLimitY();
        requestLayout();
        postInvalidate();
    }


    /**
     * 设置当前选中的列表项,将滚动到所选位置
     *
     * @param currentPosition 设置的当前位置
     */
    public void setCurrentPosition(int currentPosition) {
        setCurrentPosition(currentPosition, true);
    }

    /**
     * 设置当前选中的列表位置
     *
     * @param currentPosition 设置的当前位置
     * @param smoothScroll    是否平滑滚动
     */
    public synchronized void setCurrentPosition(int currentPosition, boolean smoothScroll) {
        if (currentPosition > mDataList.size() - 1) {
            currentPosition = mDataList.size() - 1;
        }
        if (currentPosition < 0) {
            currentPosition = 0;
        }
        if (mCurrentPosition == currentPosition) {
            return;
        }
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }

        //如果mItemHeight=0代表还没有绘制完成，这时平滑滚动没有意义
        if (smoothScroll && mItemHeight > 0) {
            mScroller.startScroll(0, mScrollOffsetY, 0, (mCurrentPosition - currentPosition) * mItemHeight);
//            mScroller.setFinalY(mScroller.getFinalY() +
//                    computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
            int finalY = -currentPosition * mItemHeight;
            mScroller.setFinalY(finalY);
            mHandler.post(mScrollerRunnable);
        } else {
            mCurrentPosition = currentPosition;
            mScrollOffsetY = -mItemHeight * mCurrentPosition;
            postInvalidate();
            if (onSelectedChangeListener != null) {
                onSelectedChangeListener.OnSelectedChange(mDataList.get(currentPosition).toString(), currentPosition);
            }
        }
    }

    /**
     * 设置数据变化监听器
     *
     * @param onSelectedChangeListener
     */
    public void setOnSelectedChangeListener(OnSelectedChangeListener onSelectedChangeListener) {
        this.onSelectedChangeListener = onSelectedChangeListener;
    }

    /**
     * 数据选择监听回调接口
     */
    public interface OnSelectedChangeListener {
        void OnSelectedChange(String item, int position);
    }
}
