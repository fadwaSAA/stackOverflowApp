package com.example.fadwasa.stackoverflowapp.usersAnswered;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
 import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOutput;
 import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
 import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersAnsweredRepository {

    private UserAnsweredInfo userInfo;
     private MutableLiveData<List<AItem>> listLiveData;


    public UsersAnsweredRepository(UserAnsweredInfo userInfo ) {
         this.userInfo = userInfo;
         listLiveData = new MutableLiveData<>();
    }




     public LiveData<List<AItem>> getResultsFromNetwork(String questionID) {

        userInfo.getAnsweredInfo(questionID,"desc","activity").enqueue(new Callback<AOutput>() {
            @Override
            public void onResponse(Call<AOutput> call, Response<AOutput> response) {
                if(response.isSuccessful()) {
                    listLiveData.setValue(response.body().getItems());

                }
            }

            @Override
            public void onFailure(Call<AOutput> call, Throwable t) {

                listLiveData.setValue(null);

            }
        });
         return listLiveData;

    }



}

