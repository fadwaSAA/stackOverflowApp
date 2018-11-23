package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;

public class ViewModel {
     private String profileImage1;
    private String name;
    private Integer accountId;
    public ViewModel(AOwner owner) {
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
        this.name = owner.getDisplayName();

    }
    public void setProfileImage1(AOwner owner) {
        profileImage1 = owner.getProfileImage();

    }
}
