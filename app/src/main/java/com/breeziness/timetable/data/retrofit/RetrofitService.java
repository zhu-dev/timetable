package com.breeziness.timetable.data.retrofit;

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
