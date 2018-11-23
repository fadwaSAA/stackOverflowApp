package com.example.fadwasa.stackoverflowapp.Users;

import com.example.fadwasa.stackoverflowapp.http.apimodel.BadgeCounts;

public class ViewModel {
    private Integer bronze;
    private String profileImage1;
    private String name;
    private Integer accountId;
    public ViewModel(String name, String profileImage, Integer accountId, BadgeCounts badgeObj) {
        this.accountId=accountId;
        this.bronze = badgeObj.getBronze();
        profileImage1 = profileImage;
         this.name = name;
    }
    public String getName() {
        return name;
    }
    public Integer getBronze() {
        return bronze;
    }
    public String getProfileImage1() {
        return profileImage1;
    }
    public Integer getAccountId() {
        return accountId;
    }

    public void setBronze(BadgeCounts badgeObj) {
        this.bronze = badgeObj.getBronze();
    }
    public void setAccountId(Integer accountId) {
        this.accountId=accountId;}



    public void setName(String name) {
        this.name = name;
    }
    public void setProfileImage1(String profileImage1) {
        this.profileImage1 = profileImage1;
    }
}
