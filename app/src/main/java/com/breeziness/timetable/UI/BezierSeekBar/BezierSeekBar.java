package com.breeziness.timetable.UI.BezierSeekBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BezierSeekBar extends View {

    private Context context;

    private int lineColor = Color.BLACK;
    private int ballColor = Color.GRAY;
    private int textColor = Color.BLUE;
    private int textSelectedColor = Color.BLUE;

    private int width;//控件宽度
    private int height;//控件高度
    private float circleRadius = 20f;//圆球半径
    private float moveX;//手指移动X坐标
    private float bezierHeight = 50f;
    private float bezierwidth = 50f;

    private Paint linePaint;
    private Paint ballPaint;
    private Paint textPaint;
    private Paint textSelectedPaint;

    private RectF totalRect;
    private RectF ballRecf;

    private Path bezierPath;

    public BezierSeekBar(Context context) {
        this(context, null);
    }

    public BezierSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initDrawRectF();
        initAttrs();
        initPaint();

        bezierPath.moveTo(100f,100f);

    }

    private void initAttrs() {
    }

    private void init(Context context) {
        this.context = context;
    }

    private void initDrawRectF() {
        totalRect = new RectF();//总区域
        ballRecf = new RectF();//总区域

        bezierPath = new Path();//初始化路径
    }

    private void initPaint() {
        //线画笔
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);

        //圆球画笔
        ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ballPaint.setColor(ballColor);
        ballPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        bezierPath.reset();
//        //画左侧直线
//        bezierPath.moveTo(0, 400);
//        bezierPath.lineTo(200, 400);

        //画左侧直线
        bezierPath.moveTo(0, height * 2 / 3.0f);
        bezierPath.lineTo(moveX - circleRadius * 2 * 3, height * 2 / 3.0f);
//
//        //画第一个三阶贝塞尔曲线 （0.4，0） （0.6，1）
//        bezierPath.moveTo(200, 400);
//        bezierPath.cubicTo(240, 400, 260, 370, 300, 370);
//
        //画第一个三阶贝塞尔曲线 （0.4，0） （0.6，1）
        bezierPath.moveTo(moveX - circleRadius * 2 * 3, height * 2 / 3.0f);
        bezierPath.cubicTo((moveX - circleRadius * 2 * 2), height * 2 / 3.0f, (moveX - circleRadius * 2 * 1), height * 2 / 3.0f - bezierHeight, moveX, height * 2 / 3.0f);
//
//        //画第二个三阶贝塞尔曲线（0.4，1）（0.6，0）
//        bezierPath.moveTo(300, 370);
//        bezierPath.cubicTo(340, 370, 360, 400, 400, 400);

        //画第二个三阶贝塞尔曲线（0.4，1）（0.6，0）
        bezierPath.moveTo(moveX, (float) 2 * height / 3 - bezierHeight);
        bezierPath.cubicTo((moveX + circleRadius * 2 * 1), (float) 2 * height / 3 - bezierHeight, (moveX + circleRadius * 2 * 1), height * 2 / 3.0f, moveX + circleRadius * 2 * 3, height * 2 / 3.0f);

//        //画右侧直线
//        bezierPath.moveTo(400, 400);
//        bezierPath.lineTo(600, 400);

        //画右侧直线
        bezierPath.moveTo(moveX + circleRadius * 2 * 3, height * 2 / 3.0f);
        bezierPath.lineTo(width, height * 2 / 3.0f);

        float valueX_start = moveX - 20f;
        float valueX_end = moveX + circleRadius + 10f;
        if (valueX_end > width) valueX_end = width;
        if (valueX_start < 0) valueX_start = 0f;

        ballRecf.set(valueX_start, height / 2f - 20f, valueX_end, height / 2f - 20f + circleRadius);

        canvas.drawPath(bezierPath, linePaint);
       // canvas.drawRoundRect(ballRecf, circleRadius, circleRadius, ballPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                if (moveX < 0f) moveX = 0f;
                if (moveX > width) moveX = width;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                if (moveX < 0f) moveX = 0f;
                if (moveX > width) moveX = width;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;


        }
        return super.onTouchEvent(event);
    }

    private float px2dp(float px) {
        float scale = getScale(context);
        return px / scale + 0.5f;
    }

    private float dp2px(float dp) {
        float scale = getScale(context);
        return dp * scale + 0.5f;
    }

    private float getScale(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
