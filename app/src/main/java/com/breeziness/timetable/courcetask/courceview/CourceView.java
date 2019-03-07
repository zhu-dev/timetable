package com.breeziness.timetable.courcetask.courceview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.breeziness.timetable.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义的课程view
 */
@SuppressLint("AppCompatCustomView")
public class CourceView extends TextView {

    private static final String TAG = "CourceView";

    public static final int M_WIDTH = 0;//测量宽度的常量
    public static final int M_HEIGHT = 1;//测量高度常量

    private int courceId;//课程ID
    private int startSection;//开始节次  确定在容器的上坐标
    private int endSection;//结束节次  确定在容器中的下坐标
    private int weekday;//星期几   确定所属的容器

    private Paint mPaint;//画笔
    private Rect mBound;//文字区域

    private int lineCount;


    private List<String> mTextList = new ArrayList<>();//文本内容容器

    //构造方法
    public CourceView(Context context) {
        this(context, null);
    }

    public CourceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //绑定自定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CourceView);
        courceId = array.getInt(R.styleable.CourceView_courceId, 0);
        startSection = array.getInt(R.styleable.CourceView_startSection, 0);
        endSection = array.getInt(R.styleable.CourceView_endSection, 0);
        weekday = array.getInt(R.styleable.CourceView_weekday, 0);
        array.recycle();

        //初始化画笔
        mPaint = new Paint();
        mBound = new Rect();
        //mPaint.setAntiAlias(true);
        // mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(30);
        mPaint.getTextBounds(getText().toString(), 0, getText().length(), mBound);

    }

    /**
     * 测量view
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 绘制         X=控件宽度/2 - 文本宽度/2；     Y=控件高度/2 + 文本宽度/2
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        //解决文本无法垂直居中的问题，动态绘制获取文本高度和TextView的宽度，计算出居中的位置 (getHeight() - textWidth) / 2）
        float textHeight = getStringHeight(getText().toString(), getPaint());
//        float textWidth = getStringWidth(getText().toString(), getPaint());
//        canvas.translate(0, ((getHeight() - textWidth) / 2));

        mPaint.setColor(Color.WHITE);
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        //float startX = (float) (getWidth() / 2) - fm.descent + (fm.bottom - fm.top) / 2;
        float startX = (float) (getWidth() / 2);
        float startY = (float) (getHeight() / 2) + (fm.bottom - fm.top) / 2;//文本的坐标在文字的左下角，故为“+”
        //int baseLineY = (int) (mBound.centerY() - fm.top/2 - fm.bottom/2);//基线中间点的y轴计算公式

        newLines(mTextList);//分割
        Log.e(TAG, "onDraw:================== " + getText().toString());
        for (int i = 0; i < mTextList.size(); i++) {
            Log.e(TAG, "mTextList:------->"+mTextList.get(i));
            canvas.drawText(mTextList.get(i), startX, (startY + textHeight * (i + 1)), mPaint);
        }
        mTextList.clear();


    }

    protected void newLines(List<String> Test) {
        String mText = getText().toString();
        int mTextWidth = mText.length();//获取文本的长度
        //容器为空才能重新分割填充
        if (mTextList.size() == 0) {
            int lineWidth = (int) mPaint.measureText("四个中文A");//一行最多显示 "四个中文A"的宽度
            //判断文字是否小于行长显示
            if (lineWidth > mTextWidth) {    //小于直接显示
                lineCount = 1;
                mTextList.add(mText);
            } else {       //大于就算出行数，准备分割
                lineCount = (int) (mTextWidth / lineWidth + 0.5f);  //获取行数
                String subText;   //缓存临时的子字符串

                //循环判断
                for (int i = 0; i < lineCount; i++) {

                    //是否这次分割后的字符串小于行长度
                    if (mText.length() < lineWidth) {
                        subText = mText;  //小于行长就直接显示
                        mText = "";//如果不够一行了 清空这个字符串
                    } else {
                        subText = mText.substring(0, lineWidth); //大于继续分割
                    }
                    mTextList.add(subText);  //加到容器中

                    //判断是否字符串已经为空
                    if (!TextUtils.isEmpty(mText)) {

                        //重新赋值mText
                        if (mText.length() > lineWidth) {
                            mText = mText.substring(lineWidth);
                        }
                    } else { //为空了，直接退出
                        break;
                    }

                }

            }
        }
    }

    /**
     * 获取文本字体的高度----px
     *
     * @param string
     * @param paint
     * @return
     */
    public float getStringHeight(String string, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(string, 0, string.length(), rect);
        return rect.height();
    }

    /**
     * 获取文本长度-----px
     *
     * @param string
     * @param paint
     * @return
     */
    public float getStringWidth(String string, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(string, 0, string.length(), rect);
        return rect.width();
    }

    /**
     * 计算控件宽高
     * EXACTLY：一般表示设置了 明确值，或者 match_parent ；
     * AT_MOST：表示子控件限制在一个最大值内，一般为 wrap_content；
     * UNSPECIFIED：表示子控件像多大就多大，很少使用
     *
     * @param attr
     * @param oldMeasure
     * @return
     */
    protected int onMeasurePre(int attr, int oldMeasure) {
        int newSize = 0;
        float value;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "onMeasurePre: ------>" + "进来了");
                if (attr == 0) {
                    //value = mBound.width(); //粗略的测量宽度
                    value = mPaint.measureText(getText().toString());//精确的测量精度
                    newSize = (int) (getPaddingLeft() + getPaddingRight() + value);
                } else if (attr == 1) {
                    //value = mBound.height();  //粗略的测量高度
                    Paint.FontMetrics fm = mPaint.getFontMetrics();
                    value = Math.abs(fm.bottom - fm.top);
                    newSize = (int) (getPaddingTop() + getPaddingBottom() + value);
                }
                break;
        }

        return newSize;
    }

    public int getCourceId() {
        return courceId;
    }


    public void setCourceId(int courceId) {
        this.courceId = courceId;
    }

    public int getStartSection() {
        return startSection;
    }

    public void setStartSection(int startSection) {
        this.startSection = startSection;
    }

    public int getEndSection() {
        return endSection;
    }

    public void setEndSection(int endSection) {
        this.endSection = endSection;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }
}
