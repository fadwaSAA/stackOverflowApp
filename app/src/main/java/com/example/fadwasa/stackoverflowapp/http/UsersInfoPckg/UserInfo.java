package com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Fadwasa on 18/11/2018 AD.
 */

public interface UserInfo {
    @GET("users")
    Call<Output> getUsersInfo(@Query("order") String order, @Query("sort") String sort);
}
