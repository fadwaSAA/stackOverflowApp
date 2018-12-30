package com.example.fadwasa.stackoverflowapp.Users;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Item;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.UserInfo;
import java.util.List;

public class UsersViewModel extends ViewModel {
    private LiveData<List<Item>> userInfo;

     private UsersRpository repository;


    public UsersViewModel() {


    }

    public UsersViewModel(UsersRpository repository) {
        this.repository=repository;
    }


    public LiveData<List<Item>> loadData() {

        if(userInfo==null){
            UserInfo userInfoM = new ApiModuleForInfo().provideApiService();
            repository=new UsersRpository(userInfoM);
            userInfo = repository.getResultData();

        }
        return userInfo;

    }

}
