package com.breeziness.timetable.UI.editText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;
import com.breeziness.timetable.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;


@SuppressLint("AppCompatCustomView")
public class NumberInputView extends EditText {

    private float borderWidth = 5.0f;//边框粗细
    private float borderRadius = 3.0f;//边框圆角
    private int borderColor = 0xFFCCCCCC;//边框颜色
    private int textColor = 0xFFCCCCCC;//输入数字/圆点的颜色
    private float circleDot = 3.0f;//如果是密码暗文模式，圆点大小
    private int textLenght = 2;//数字/密码长度
    private boolean isPassword = false;//是否是暗文模式

    private Paint mPaint;//画笔
    private int height;//高度
    private int width;//宽度

    private OnInputCompleteListener onInputCompleteListener;//输入完成监听回调接口

    public NumberInputView(Context context) {
        super(context);
    }

    public NumberInputView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //将设置的默认值非标准单位转化为标准单位
        DisplayMetrics dm = getResources().getDisplayMetrics();
        borderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderWidth, dm);//sp->px
        borderRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderRadius, dm);//sp->px
        textLenght = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textLenght, dm);//sp->px

        //获得自定义的属性值
        // TypedArray array = context.getTheme().obtainStyledAttributes();使用这个方法必须将属性加到自定义的theme中引用
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberInputView, 0, 0);
        borderWidth = array.getDimension(R.styleable.NumberInputView_borderWidth, borderWidth);
        borderRadius = array.getDimension(R.styleable.NumberInputView_borderRadius, borderRadius);
        borderColor = array.getColor(R.styleable.NumberInputView_borderColor, borderColor);
        textColor = array.getColor(R.styleable.NumberInputView_textColor, textColor);
        circleDot = array.getDimension(R.styleable.NumberInputView_circleDot, circleDot);
        textLenght = array.getInt(R.styleable.NumberInputView_textLenght, textLenght);
        isPassword = array.getBoolean(R.styleable.NumberInputView_isPassword, isPassword);
        array.recycle();//typedArray是从一个pool中取出的，用完一定要recycle(),回收内存

        mPaint = new Paint(ANTI_ALIAS_FLAG);//抗锯齿  antialias

        //设置默认属性
        this.setEms(textLenght);
        this.setMaxLines(1);
        this.setFocusable(true);
        this.setCursorVisible(false);
        this.setBackgroundColor(Color.TRANSPARENT);

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
        //获取控件的宽高
        width = getWidth();
        height = getHeight();
        drawBorder(canvas);
        drawBackground(canvas);
        drawDivider(canvas);
        drawDot(canvas);

    }

    /**
     * 绘制边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);//画外线模式
        mPaint.setColor(borderColor);//框颜色
        mPaint.setStrokeWidth(borderWidth);//框线粗细
        RectF rf = new RectF(0, 0, width, height);//创建一个矩形
        canvas.drawRoundRect(rf, borderRadius, borderRadius, mPaint);//画出圆角矩形

    }

    private void drawBackground(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        RectF rf = new RectF(borderWidth, borderWidth, width - borderWidth, height - borderWidth);//创建一个矩形,画在边框里
        canvas.drawRoundRect(rf, borderRadius, borderRadius, mPaint);//画出圆角矩形背景
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     */
    private void drawDivider(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(borderColor);
        mPaint.setStrokeWidth(0);
        //去确定分割线x坐标，画竖线，画（textLenght）次
        for (int i = 0; i < textLenght; i++) {
            int x = i * (width / textLenght);
            canvas.drawLine(x, 0, x, height, mPaint);
        }
    }

    /**
     * 绘制圆点
     *
     * @param canvas
     */
    private void drawDot(Canvas canvas) {
        String content = getText().toString().trim();//获取去掉空格后的内容文本

        //判断是密码模式，还是数字明文模式
        if (isPassword) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(textColor);

            //画圆点
            for (int i = 0; i < content.length(); i++) {
                int x = (width / textLenght * i) + (width / (2 * textLenght));//计算方式  x(i) =(width / textLenght * i) + (width / (2 * textLenght))  第i框的分割线坐标+半个框长度
                int y = height / 2;//半个框
                int radius = width / textLenght / 10;//直径为框长的1/10
                if (radius <= circleDot) {
                    radius = (int) circleDot;
                }
                canvas.drawCircle(x, y, radius, mPaint);
            }
        } else {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(textColor);
            mPaint.setTextSize(getWidth() / 10.0f);
            Paint.FontMetrics fm = mPaint.getFontMetrics();//以字体的显示baseline  获取各个坐标
            //画出数字
            for (int i = 0; i < content.length(); i++) {
                int x = (int) ((width / textLenght * i) + (width / (2 * textLenght)) - getWidth() / 50.0f);//这里适配可能会出现问题 ，这里除以50是粗糙的居中方法
                int y = (int) (height / 2.0f + (fm.bottom - fm.top) / 2 - fm.descent);//半个框
                canvas.drawText(content.substring(i, i + 1), x, y, mPaint);
            }
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        invalidate();//刷新UI重新绘制
        //输入完成监听回调接口
        if (text.length() == textLenght) {
            if (onInputCompleteListener != null)
                onInputCompleteListener.onComplete(text.toString());
        }
    }

    /**
     * 设置输入完成监听器
     *
     * @param onInputCompleteListener
     */
    public void setOnInputCompleteListener(OnInputCompleteListener onInputCompleteListener) {
        this.onInputCompleteListener = onInputCompleteListener;
    }

    /**
     * 输入完成监听回调接口
     */
    public interface OnInputCompleteListener {
        void onComplete(String content);
    }
}
