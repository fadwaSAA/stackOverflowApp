package com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserInfo {
    @GET("users")
    Observable<Output> getUsersInfo(@Query("order") String order, @Query("sort") String sort);
}
