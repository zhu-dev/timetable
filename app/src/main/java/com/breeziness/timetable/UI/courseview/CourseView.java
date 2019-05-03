package com.breeziness.timetable.UI.courseview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义的课程view
 */
@SuppressLint("AppCompatCustomView")
public class CourseView extends TextView {

    private static final String TAG = "CourceView";

    private int startSection;//开始节次  确定在容器的上坐标
    private int endSection;//结束节次  确定在容器中的下坐标
    private int courceId;//课程ID
    private int weekday;//星期几   确定所属的容器
    private String cname;//课程名字
    private String name;//教师名字
    private String courseno;//课程代号
    private int startweek;//开始周次
    private int endweek;//结束周次
    private String seq;//第几节

    private Paint mPaint;//画笔

    private int lineCount;//行数


    private List<String> mTextList = new ArrayList<>();//文本内容容器

    //构造方法
    public CourseView(Context context) {
        this(context, null);
    }

    public CourseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        mPaint = new Paint();


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
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        newLines(mTextList);//分割获得行数
        // Paint.FontMetrics fm = mPaint.getFontMetrics();//获得文本参数
        float scale = getContext().getResources().getDisplayMetrics().density;//获取屏幕密度，相对密度，近似
        float Y = (getHeight() - getStringHeight(getText().toString(), mPaint) * lineCount * scale) / 2;//加上scale效果更好
        canvas.translate(0, Y);//加入文本位置偏移
        mTextList.clear();

        super.onDraw(canvas);

    }

    private void newLines(List<String> mlist) {
        String mText = getText().toString();

        float mTextWidth = mPaint.measureText(mText);//获取文本的长度

        //容器为空才能重新分割计算
        if (mlist.size() == 0) {
            int lineWidth = (int) mPaint.measureText("四个中文A");//一行最多显示 "四个中文A"的宽度

            //判断文字是否小于行长显示
            if (lineWidth > mTextWidth) {    //小于直接显示
                lineCount = 1;
            } else {       //大于就算出行数
                lineCount = (int) (mTextWidth / lineWidth + 0.5f);  //获取行数
            }
        }
        mTextList = mlist;//更新集合
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

    public int getCourceId() {
        return courceId;
    }


    public void setCourceId(int courceId) {
        this.courceId = courceId;
       // Log.e(TAG, "setCourceId: ---courceId--"+courceId);
    }

    public int getStartSection() {
        return startSection;
    }

    public void setStartSection(int startSection) {
        this.startSection = startSection;
       // Log.e(TAG, "setCourceId: ---startSection--"+startSection);
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
       // Log.e(TAG, "setCourceId: ---weekday--"+weekday);
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseno() {
        return courseno;
    }

    public void setCourseno(String courseno) {
        this.courseno = courseno;
    }

    public int getStartweek() {
        return startweek;
    }

    public void setStartweek(int startweek) {
        this.startweek = startweek;
    }

    public int getEndweek() {
        return endweek;
    }

    public void setEndweek(int endweek) {
        this.endweek = endweek;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
        startSection = endSection = Integer.valueOf(seq);
    }

}
