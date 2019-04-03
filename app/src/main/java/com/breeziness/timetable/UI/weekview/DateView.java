package com.breeziness.timetable.UI.weekview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.breeziness.timetable.R;

import androidx.annotation.Nullable;

public class DateView extends View {

    private static final String TAG = "DateView";

    private Context context;

    private int textSize;
    private int textColor;
    private int selectedDateTextColor;
    private int selectedDateCircleColor;

    private Paint textPaint;
    private Paint curDateTextPaint;

    private Paint mPaint;

    private Rect totalDrawnRect;
    private Rect circleDrawnRect;

    int width;
    int height;

    private boolean isSelectedDate = true;
    private String mText = "20";


    public DateView(Context context) {
        this(context, null);
    }

    public DateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        drawRectsInit();
        initAttrs(context, attrs);
        initPaints();
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

    private void drawRectsInit() {
        totalDrawnRect = new Rect();//总绘制区域
        circleDrawnRect = new Rect();//圆形选中框
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

        mPaint.setColor(getResources().getColor(R.color.color_light_grey));
        totalDrawnRect.set(0, 0, width, height);
        canvas.drawRect(totalDrawnRect, mPaint);

        if (isSelectedDate) {
            drawCircle(canvas, mPaint);
            drawText(canvas, curDateTextPaint);
        } else {
            drawText(canvas, textPaint);
        }
    }

    private void drawText(Canvas canvas, Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();//获取字体的显示基于baseline的各个参数
        float x = width / 2.0f;
        float y = (height + (fm.bottom - fm.top)) / 2 - fm.descent;
        canvas.drawText(mText, x, y, paint);
    }

    private void drawCircle(Canvas canvas, Paint paint) {
        circleDrawnRect.set(0, 0, width / 2, width / 2);
        paint.setColor(selectedDateCircleColor);
        float radius = width / 4.0f;
        float x = width / 2.0f;
        float y = height / 2.0f;
        canvas.drawCircle(x, y, radius, paint);
    }

    public void setContentText(String mText) {
        this.mText = mText;
    }
    public void setSelectedDate(boolean selectedDate) {
        isSelectedDate = selectedDate;
    }


}
