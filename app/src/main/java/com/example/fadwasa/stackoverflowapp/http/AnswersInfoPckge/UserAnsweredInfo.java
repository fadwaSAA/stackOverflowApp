package com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAnsweredInfo {

    @GET("questions/{id}/answers")
    Call<AOutput> getAnsweredInfo(@Path("id") String id,
                                  @Query("order") String order,
                                  @Query("sort") String sort);
}
