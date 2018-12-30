package com.example.fadwasa.stackoverflowapp.Users;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
 import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Item;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Output;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.UserInfo;
 import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersRpository {
    final  MutableLiveData<List<Item>> usersObservable;
    private UserInfo userInfo;


    public UsersRpository(UserInfo userInfo) {

        usersObservable = new MutableLiveData<>();
         this.userInfo = userInfo;
     }





     public LiveData<List<Item>> getResultsFromNetwork() {


        userInfo.getUsersInfo("desc","reputation").enqueue(new Callback<Output>() {
            @Override
            public void onResponse(Call<Output> call, Response<Output> response) {
                if(response.isSuccessful()) {
                    usersObservable.setValue(response.body().getItems());

                }
            }

            @Override
            public void onFailure(Call<Output> call, Throwable t) {

                usersObservable.setValue(null);

            }
        });

        return usersObservable;


    }




     public LiveData<List<Item>> getResultData() {
         LiveData<List<Item>> results=getResultsFromNetwork();
        return results;

    }

}

