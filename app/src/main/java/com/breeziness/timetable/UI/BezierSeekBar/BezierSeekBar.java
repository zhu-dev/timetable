package com.breeziness.timetable.UI.BezierSeekBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.breeziness.timetable.R;
import com.breeziness.timetable.util.DecimalFormat;
import com.breeziness.timetable.util.SharedPreferencesUtil;

import androidx.annotation.Nullable;

public class BezierSeekBar extends View {

    private static final String TAG = "BezierSeekBar";

    private Context mContext;

    private int valueSelected;
    private int valueMin = 1;
    private int valueMax = 20;
    private String unit = "周";//显示的单位
    private int defaultWidth;//默认宽度
    private int defaultHeight = defaultWidth / 3;//默认高度
    private int valueTextSize = 20;

    private int lineColor = Color.BLACK;
    private int ballColor = Color.GRAY;
    private int textColor = Color.BLUE;
    private int valueSelectedColor = Color.RED;
    private int valueBgColor = Color.GRAY;

    private int width;//控件宽度
    private int height;//控件高度
    private int bezierWidth = 50;//贝塞尔曲线宽度
    private float bezierHeight = bezierWidth * 0.7f;
    private float lastMoveX = bezierWidth * 2 * 3;//手指移动X坐标
    private float moveX = lastMoveX;//手指移动X坐标

    private Paint mPaint;
    private Paint linePaint;
    private Paint ballPaint;
    private Paint textPaint;
    private Paint textSelectedPaint;
    private Paint valueBgPaint;

    private RectF totalRect;
    private RectF ballRect;
    private RectF valueRect;//选中位置文字的填充框

    private Path bezierPath;

    private OnSelectedValueListener onSelectedValueListener;//数值选择回调接口

    public BezierSeekBar(Context mContext) {
        this(mContext, null);
        //init(mContext, null);
    }

    public BezierSeekBar(Context mContext, @Nullable AttributeSet attrs) {
        this(mContext, attrs, 0);
        // init(mContext, attrs);
    }

    public BezierSeekBar(Context mContext, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        init(mContext, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        initDrawRectF();
        initAttrs(attrs);
        initPaint();
        bezierPath = new Path();//初始化路径
        //moveX = bezierWidth * 2 * 3;//手指移动X坐标
        valueSelected = getCurrent();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BezierSeekBar);
            lineColor = array.getColor(R.styleable.BezierSeekBar_lineColor, lineColor);
            textColor = array.getColor(R.styleable.BezierSeekBar_commonTextColor, textColor);
            ballColor = array.getColor(R.styleable.BezierSeekBar_commonBallColor, ballColor);
            valueBgColor = array.getColor(R.styleable.BezierSeekBar_selectedBgColor, valueBgColor);
            valueSelectedColor = array.getColor(R.styleable.BezierSeekBar_selectedValueColor, valueSelectedColor);
            valueTextSize = array.getDimensionPixelSize(R.styleable.BezierSeekBar_valueTextSize, valueTextSize);
            bezierWidth = array.getDimensionPixelSize(R.styleable.BezierSeekBar_bezierWidth, (int) dp2px(8));
            array.recycle();
        }
    }

    private void initDrawRectF() {
        totalRect = new RectF();//总区域
        ballRect = new RectF();//圆球区域
        valueRect = new RectF();//文字填充框

    }

    private void initPaint() {

        //主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);

        //线画笔
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(dp2px(1));
        linePaint.setFakeBoldText(true);

        //圆球画笔
        ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ballPaint.setColor(ballColor);
        ballPaint.setStyle(Paint.Style.FILL);

        //文字画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(valueTextSize);
        textPaint.setStyle(Paint.Style.FILL);

        //选中文字画笔
        textSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textSelectedPaint.setColor(valueSelectedColor);
        textSelectedPaint.setTextSize(valueTextSize);
        textSelectedPaint.setStyle(Paint.Style.FILL);

        //选中的数值背景填充框画笔
        valueBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valueBgPaint.setColor(valueBgColor);
        valueBgPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        defaultWidth = (int) dp2px(100);
        width = computeSize(defaultWidth, widthMeasureSpec);
        height = computeSize(defaultHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);

        int currentWeek = getCurrent();//获得当前周次
        //通过数值获得格式化算法反算触摸点位置
        moveX = (currentWeek-valueMin)*(width-bezierWidth * 2 * 4f)/(valueMax - valueMin)+bezierWidth * 2 * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mPaddingStart = getPaddingStart();
        int mPaddingEnd = getPaddingEnd();
        int mPaddingTop = getPaddingTop();
        int mPaddingBottom = getPaddingBottom();

        width = getWidth() - mPaddingStart - mPaddingEnd;
        height = getHeight() - mPaddingTop - mPaddingBottom;

        bezierPath.reset();
      //  Log.d(TAG, "onDraw: ----moveX---" + moveX);
        //画左侧直线
        bezierPath.moveTo(0, height * 2 / 3.0f);
        bezierPath.lineTo(moveX - bezierWidth * 2 * 2, height * 2 / 3.0f);


        //画第一个三阶贝塞尔曲线
        bezierPath.moveTo(moveX - bezierWidth * 2 * 2, height * 2 / 3.0f);
        bezierPath.cubicTo((moveX - bezierWidth * 2 * 1.4f), height * 2 / 3.0f, moveX - bezierWidth * 2 * 0.8f, height * 2 / 3.0f - bezierHeight, moveX, height * 2 / 3.0f - bezierHeight);

        //画第二个三阶贝塞尔曲线
        bezierPath.moveTo(moveX, (float) 2 * height / 3 - bezierHeight);
        bezierPath.cubicTo(moveX + bezierWidth * 2 * 0.8f, (float) 2 * height / 3 - bezierHeight, moveX + bezierWidth * 2 * 1.4f, height * 2 / 3.0f, moveX + bezierWidth * 2 * 2, height * 2 / 3.0f);


        //画右侧直线
        bezierPath.moveTo(moveX + bezierWidth * 2 * 2, height * 2 / 3.0f);
        bezierPath.lineTo(width, height * 2 / 3.0f);

        String text = valueSelected + unit;
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float valueHeight = fm.bottom - fm.top;

        float valueY_start = 2 * height / 3f - valueHeight - fm.descent;
        float valueX_start = moveX - textPaint.measureText(text) / 2;
        float valueX_end = valueX_start + textPaint.measureText(text);
        if (valueX_end > width) valueX_end = width;
        if (valueX_start < 0) valueX_start = 0f;

        float valueX_Bg_start = valueX_start - 10f;
        float valueX_Bg_end = valueX_end + 10f;
        if (valueX_Bg_end > width) valueX_Bg_end = width;
        if (valueX_Bg_start < 0) valueX_Bg_start = 0f;

        valueRect.set(valueX_Bg_start, 2 * height / 3f - valueHeight * 2, valueX_Bg_end, 2 * height / 3f - valueHeight);
        totalRect.set(mPaddingStart, mPaddingTop, width + mPaddingStart, height + mPaddingTop);

        canvas.drawRect(totalRect, mPaint);//总区域
        canvas.drawRect(valueRect, valueBgPaint);//选中文字填充物

        canvas.drawText(text, valueX_start, valueY_start, textPaint);

        canvas.drawText(valueMin + "", textPaint.measureText("1"), 2 * height / 3f + fm.ascent, textPaint);//往上一个ascent（负数）距离
        canvas.drawText(valueMax + "", width - textPaint.measureText("20"), 2 * height / 3f + fm.ascent, textPaint);

        canvas.drawPath(bezierPath, linePaint);

        canvas.drawCircle(moveX, height * 2 / 3.0f, bezierWidth * 1.3f, ballPaint);

        onSelectedValueListener.onSelectedValue(valueSelected);//回调选择的值
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                lastMoveX = moveX;
                if (moveX - bezierWidth * 2 * 2 <= 0) moveX = bezierWidth * 2 * 2;
                if (moveX + bezierWidth * 2 * 2 > width) moveX = width - bezierWidth * 2 * 2;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                if (currentX <= lastMoveX + bezierWidth * 2 || currentX >= lastMoveX - bezierWidth * 2) {
                    moveX = currentX;
                    if (moveX - bezierWidth * 2 * 2 <= 0) moveX = bezierWidth * 2 * 2;
                    if (moveX + bezierWidth * 2 * 2 > width) moveX = width - bezierWidth * 2 * 2;
                    postInvalidate();
                } else {
                    moveX = lastMoveX;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        valueSelected = DecimalFormat.format(valueMin + (valueMax - valueMin) * (moveX - bezierWidth * 2 * 2) / (width - bezierWidth * 2 * 4));//获得当前触摸位置对应的数值
        return true;//拦截触摸事件
    }

    private int computeSize(int defaultSize, int measureSpec) {
        int mSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                mSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                mSize = size;
                break;
            case MeasureSpec.EXACTLY:
                mSize = size;
                break;
        }
        return mSize;
    }

    private float px2dp(float px) {
        float scale = getScale();
        return px / scale + 0.5f;
    }

    private float dp2px(float dp) {
        float scale = getScale();
        return dp * scale + 0.5f;
    }

    private int getCurrent(){
        return SharedPreferencesUtil.getInt(mContext, "CurrentWeek", "curweek", 1);
    }
    private float getScale() {
        return mContext.getResources().getDisplayMetrics().density;
    }


    public void setOnSelectedValueListener(OnSelectedValueListener onSelectedValueListener) {
        this.onSelectedValueListener = onSelectedValueListener;
    }

    public interface OnSelectedValueListener {
        void onSelectedValue(int value);
    }
}
