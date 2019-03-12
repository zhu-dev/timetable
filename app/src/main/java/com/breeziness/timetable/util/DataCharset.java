package com.breeziness.timetable.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class DataCharset {

    private static final String TAG = "DataCharset";

    /**
     * 将UTF-8的格式转化为GBK
     *
     * @param utf
     * @return
     */
    public static String utfToGbk(String utf) {
        String mGbk = null;
        if (!utf.isEmpty()) {
            byte[] transStr;
            try {
                transStr = utf.getBytes(StandardCharsets.UTF_8);
                mGbk = new String(transStr, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return mGbk;
    }

    /**
     * 判断是否是三种常见编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode;
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(), encode)))
                return encode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(), encode))) {
                return encode;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        encode = "ASCII";
        try {
            if (str.equals(new String(str.getBytes(), encode)))
                return encode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "unknown encoding";
    }
}
