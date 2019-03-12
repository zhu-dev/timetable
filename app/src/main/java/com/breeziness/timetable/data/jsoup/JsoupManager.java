package com.breeziness.timetable.data.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class JsoupManager {


    /**
     * 模拟Post请求
     *
     * @return
     */
    public static String httpPost(String url, Map<String, String> param, String cookie) {




        Connection con = Jsoup.connect(url);//获得connect对象
        if (param != null) {
            //获得参数
            for (Map.Entry<String, String> entry : param.entrySet()) {
                con.data(entry.getKey(), entry.getValue());
            }
        }
        con.postDataCharset("GBK");
        con.header("Accept", "text/html,application/xhtml+xm…ml;q=0.9,image/webp,*/*;q=0.8");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("Cookie", cookie);
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0");
        Document doc = null;
        String doc2 = null;
        try {
            //doc = con.post();//获取内容
            doc2 = con.method(Connection.Method.POST).execute().body();//获取内容
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc2 != null) {
            return doc2;
        } else {
            return "";
        }
    }

}
