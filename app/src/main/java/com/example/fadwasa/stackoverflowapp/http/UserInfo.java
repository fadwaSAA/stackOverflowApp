package com.example.fadwasa.stackoverflowapp.http;

import com.example.fadwasa.stackoverflowapp.http.apimodel.Output;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Fadwasa on 18/11/2018 AD.
 */

public interface UserInfo {
    @GET("users")
    Observable<Output> getUsersInfo(@Query("order") String order, @Query("sort") String sort);
}
