package com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface QuestionInfo {
    @GET("users/{id}/questions")
    Call<QOutput> getQuestionsInfo(@Path("id") String id,
                                   @Query("order") String order,
                                   @Query("sort") String sort);
}
