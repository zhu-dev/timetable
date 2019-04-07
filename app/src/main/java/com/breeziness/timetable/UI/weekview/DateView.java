package com.breeziness.timetable.UI.weekview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.breeziness.timetable.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DateView extends View {

    private static final String TAG = "DateView";

    private Context context;

    private int textSize;
    private int textColor;
    private int selectedDateTextColor;
    private int selectedDateCircleColor;

    private boolean isDrawUnitBackground = false;

    private Paint textPaint;
    private Paint curDateTextPaint;

    private Paint mPaint;

    private Rect totalDrawnRect;
    private Rect containDrawnRect;

    int width;
    int height;

    int unitWidth;
    int unitHeight;

    private int selectedWeekday = 0;
    // private String mText = "20";
    private List<Integer> mTextList = new ArrayList<>();


    public DateView(Context context) {
        this(context, null);
    }

    public DateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        initPaints();
        initDrawRects();
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DateView);
        textSize = array.getDimensionPixelSize(R.styleable.DateView_commomTextSize, textSize);
        textColor = array.getColor(R.styleable.DateView_commomTextColor, textColor);
        selectedDateTextColor = array.getColor(R.styleable.DateView_selectedTextColor, selectedDateTextColor);
        selectedDateCircleColor = array.getColor(R.styleable.DateView_selectedCircleColor, selectedDateCircleColor);
        array.recycle();

    }

    private void initPaints() {

        //主画笔,绘制区域
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);

        //通用字体画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);

        //当前天数字体画笔
        curDateTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        curDateTextPaint.setColor(selectedDateTextColor);
        curDateTextPaint.setStyle(Paint.Style.FILL);
        curDateTextPaint.setTextAlign(Paint.Align.CENTER);
        curDateTextPaint.setTextSize(textSize);

    }

    private void initDrawRects() {
        totalDrawnRect = new Rect();//总绘制区域
        containDrawnRect = new Rect();//将主空间分为7分  这是每一份的区域
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        totalDrawnRect.set(0, 0, width, height);
        canvas.drawRect(totalDrawnRect, mPaint);

        unitWidth = width / 7;
        unitHeight = height;

        for (int i = 0; i < 7; i++) {
            if (isDrawUnitBackground) {
                mPaint.setColor(getResources().getColor(R.color.color_light_grey));
                containDrawnRect.set(unitWidth * i, 0, (i + 1) * unitWidth, unitHeight);
                canvas.drawRect(containDrawnRect, mPaint);
            }
            Log.e(TAG, "onDraw: -----date--"+mTextList.get(i+2));
            if ((selectedWeekday - 1) == i) {
                //Log.e(TAG, "setContentText: ----Weekday---" + selectedWeekday);
                drawCircle((unitWidth * i) + unitWidth / 2.0f, unitHeight / 2.0f, unitWidth / 4.0f, canvas, mPaint);
                if (mTextList.size() != 0) {
                    drawText(mTextList.get(i + 2).toString(), (unitWidth * i) + unitWidth / 2.0f, unitHeight, canvas, curDateTextPaint);
                }

            } else {
                if (mTextList.size() != 0) {
                    drawText(mTextList.get(i + 2).toString(), (unitWidth * i) + unitWidth / 2.0f, unitHeight, canvas, textPaint);
                }

            }


        }
    }

    private void drawText(String text, float x, float y, Canvas canvas, Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();//获取字体的显示基于baseline的各个参数
        canvas.drawText(text, x, (y + (fm.bottom - fm.top)) / 2 - fm.descent, paint);
    }

    private void drawCircle(float x, float y, float radius, Canvas canvas, Paint paint) {
        paint.setColor(selectedDateCircleColor);
        canvas.drawCircle(x, y, radius, paint);
    }

    public void setContentText(List<Integer> mTextList) {
        this.mTextList = mTextList;
        int weekday = mTextList.get(1) - 1;
        if (weekday == 0) {
            weekday = 7;
        }
        setSelectedWeekday(weekday);
       // Log.e(TAG, "setContentText: ----SelectedWeekday---" + selectedWeekday);
        invalidate();
    }

    public void setSelectedWeekday(int selectedWeekday) {
        this.selectedWeekday = selectedWeekday;
    }

    public void setDrawUnitBackground(boolean drawUnitBackground) {
        isDrawUnitBackground = drawUnitBackground;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public void setSelectedDateTextColor(int selectedDateTextColor) {
        this.selectedDateTextColor = selectedDateTextColor;
        invalidate();
    }

    public void setSelectedDateCircleColor(int selectedDateCircleColor) {
        this.selectedDateCircleColor = selectedDateCircleColor;
        invalidate();
    }

}
