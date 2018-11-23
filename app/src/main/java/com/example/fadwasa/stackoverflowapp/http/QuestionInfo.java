package com.example.fadwasa.stackoverflowapp.http;
import com.example.fadwasa.stackoverflowapp.http.apimodel.QOutput;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Fadwasa on 20/11/2018 AD.
 */

public interface QuestionInfo {
    @GET("users/{id}/questions")
    Observable<QOutput> getQuestionsInfo(@Path("id") String id,
                                         @Query("order") String order,
                                         @Query("sort") String sort);
}
