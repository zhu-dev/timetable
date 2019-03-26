package com.breeziness.timetable.util;

import android.graphics.Color;

/**
 * 颜色渐变器工具类
 * 获得某个百分比点的渐变颜色
 */
public class LinearGradientUtil {
    private int startColor;
    private int endColor;

    public LinearGradientUtil(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public int getColor(float colorRadio){
        //获得起始颜色和结束颜色的RGB
        int startRed = Color.red(startColor);
        int startGreen = Color.green(startColor);
        int startBlue = Color.blue(startColor);
        int endRed = Color.red(endColor);
        int endGreen = Color.green(endColor);
        int endBlue = Color.blue(endColor);

        //颜色渐变
        int red = (int) ((endRed-startRed)*colorRadio+0.5);
        int green = (int) ((endGreen-startGreen)*colorRadio+0.5);
        int blue = (int) ((endBlue-startBlue)*colorRadio+0.5);

        return Color.argb(255,red,green,blue);//合成颜色
    }
}
