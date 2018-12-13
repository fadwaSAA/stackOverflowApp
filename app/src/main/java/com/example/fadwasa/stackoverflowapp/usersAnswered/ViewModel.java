package com.example.fadwasa.stackoverflowapp.usersAnswered;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;

public class ViewModel implements Parcelable{
     private String profileImage1;
    private String name;
    private Integer accountId;
    public ViewModel(AOwner owner) {
        this.accountId=owner.getUserId();
         profileImage1 = owner.getProfileImage();
         this.name = owner.getDisplayName();
    }

    protected ViewModel(Parcel in) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
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
