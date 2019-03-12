package com.breeziness.timetable.data.retrofit;



import com.breeziness.timetable.data.bean.LoginBean;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface RetrofitService {


    //获取验证图片
    //http://bkjw.guet.edu.cn/  http://bkjw.guet.edu.cn/login/GetValidateCode      http://bkjw.guet.edu.cn/login/GetValidateCode?id=0.20388818644303797
    @GET("login/GetValidateCode")
    @Headers({"User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0", "Accept:image/webp,*/*"})
    Observable<ResponseBody> getIdImage();

    //获取cookie session
    //http://bkjw.guet.edu.cn/Login/SubmitLogin
    @POST("Login/SubmitLogin")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0")
    Observable<LoginBean> getCookie(@QueryMap Map<String, String> params);

    //获取课表的网页html
    @POST()
    Observable<String> getCourseHtml(@QueryMap Map<String, String> params);

    //获取我的成绩
    @GET()
    Observable<String> getScore();

    //获取有道词典翻译
    @POST()
    Observable<String> getYouDao();

    //获取金山翻译结果
    @GET()
    Observable<String> getJinshan();


    //    测试我的服务器登录功能
//    @POST("login.php")
//    Observable<TestBean> login(@QueryMap Map<String, String> param);


//    //    测试访问学校教务系统
//    @POST("public/login.asp")
//    Observable<String> login(@QueryMap Map<String, String> paramsMap);
////    //使用POST方式，后面添加path
////    //根据请求头格式 ww-form-urlencoded
////    //field 参数为三个  username   password  login =%B5%C7%A1%A1%C2%BC//    测试访问学校教务系统
//
//    @POST("selectterm.asp")
//    Observable<String> getCourse(@QueryMap Map<String, String> paramsMap);
////    //使用POST方式，后面添加path
////    //根据请求头格式 ww-form-urlencoded
////    //field 参数为三个  username   password  login =%B5%C7%A1%A1%C2%BC


}
