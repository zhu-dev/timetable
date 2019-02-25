package com.breeziness.timetable.data.retrofit;

import com.breeziness.timetable.data.CourceBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitService {

    /**
     *
     * @return
     */
    @GET("news/latest")
    Observable<String> getCourceBy();
}
