package com.example.fadwasa.stackoverflowapp.Users;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.BadgeCounts;

public class ViewModel implements Parcelable{
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

    protected ViewModel(Parcel in) {
        if (in.readByte() == 0) {
            bronze = null;
        } else {
            bronze = in.readInt();
        }
        profileImage1 = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            accountId = null;
        } else {
            accountId = in.readInt();
        }
    }

    public static final Creator<ViewModel> CREATOR = new Creator<ViewModel>() {
        @Override
        public ViewModel createFromParcel(Parcel in) {
            return new ViewModel(in);
        }

        @Override
        public ViewModel[] newArray(int size) {
            return new ViewModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (bronze == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(bronze);
        }
        parcel.writeString(profileImage1);
        parcel.writeString(name);
        if (accountId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(accountId);
        }
    }
}
