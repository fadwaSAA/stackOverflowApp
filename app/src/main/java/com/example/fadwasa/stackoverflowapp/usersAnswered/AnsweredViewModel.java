package com.example.fadwasa.stackoverflowapp.usersAnswered;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import java.util.List;

public class AnsweredViewModel extends ViewModel{
     private String profileImage1;
    private String name;
    private Integer accountId;
    private LiveData<List<AItem>> userAnsweredInfo;
    private UsersAnsweredRepository repository;

    public AnsweredViewModel() {

    }

    public AnsweredViewModel(UsersAnsweredRepository repository) {
        this.repository=repository;
    }

    public AnsweredViewModel(AOwner owner) {
        this.accountId=owner.getUserId();
         profileImage1 = owner.getProfileImage();
         this.name = owner.getDisplayName();
    }




    public String getName() {
        return name;
    }

    public String getProfileImage1() {
        return profileImage1;
    }
    public Integer getAccountId() {
        return accountId;
    }



    public void setAccountId(AOwner owner) {
         this.accountId=owner.getUserId();
     }
    public void setName(AOwner owner) {
        this.name = owner.getDisplayName();}
    public void setProfileImage1(AOwner owner) {
        profileImage1 = owner.getProfileImage();}



    public LiveData<List<AItem>> loadData(String questionID) {

        if(userAnsweredInfo==null){
            UserAnsweredInfo userAnsweredInfo1 = new ApiModuleForInfo().provideApiServiceA();
            repository=new UsersAnsweredRepository(userAnsweredInfo1);
            userAnsweredInfo = repository.getResultsFromNetwork(questionID);

        }
        return userAnsweredInfo;

    }
}
