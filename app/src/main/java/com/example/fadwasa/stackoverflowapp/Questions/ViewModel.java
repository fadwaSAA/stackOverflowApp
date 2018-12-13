package com.example.fadwasa.stackoverflowapp.Questions;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.Owner;

import java.util.List;

public class ViewModel implements Parcelable{
    private Integer userID;
    private String profileImage1;
    private String Answeredname;

    private Integer acceptedAnswetID;
    private Integer answerCount;
    private Integer questionID;
    private String title;
    private Owner owner;

    public ViewModel(){
        this.userID = null;
        this.profileImage1 = null;
        this.Answeredname = null;
        this.acceptedAnswetID = null;
        this.answerCount = null;
        this.questionID = null;
        this.title = null;
        this.owner = null;
    }

    public ViewModel(Integer acceptedAnswetID, Integer answerCount, Integer questionID, String title, Owner owner, AOwner aOwner) {

        this.acceptedAnswetID=acceptedAnswetID;
        this.answerCount=answerCount;
        this.questionID=questionID;
        this.title=title;
        this.owner=owner;
        this.profileImage1 = aOwner.getProfileImage();
        this.Answeredname = aOwner.getDisplayName();
    }

    public ViewModel(Integer acceptedAnswerId, Integer answerCount, Integer questionId, String title, Owner owner) {
        this.acceptedAnswetID=acceptedAnswerId;
        this.answerCount=answerCount;
        this.questionID=questionId;
        this.title = title;
        this.owner=owner;
    }

    protected ViewModel(Parcel in) {
        if (in.readByte() == 0) {
            userID = null;
        } else {
            userID = in.readInt();
        }
        profileImage1 = in.readString();
        Answeredname = in.readString();
        if (in.readByte() == 0) {
            acceptedAnswetID = null;
        } else {
            acceptedAnswetID = in.readInt();
        }
        if (in.readByte() == 0) {
            answerCount = null;
        } else {
            answerCount = in.readInt();
        }
        if (in.readByte() == 0) {
            questionID = null;
        } else {
            questionID = in.readInt();
        }
        title = in.readString();
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
        return Answeredname;
    }
    public String getProfileImage1() {
        return profileImage1;
    }
    public Integer getAcceptedAnswetID() {
        return acceptedAnswetID;
    }
    public Integer getAnswerCount() {
        return answerCount;
    }
    public Integer getQuestionID() {
        return questionID;
    }
    public String getTitle() {
        return title;
    }


    public void setProfileImage1(Owner owner) {
        this.profileImage1=owner.getProfileImage();
    }

    public void setUserID(Owner owner) {
        this.userID=owner.getUserId();
    }

    public void setName(Owner owner) {
        this.Answeredname=owner.getDisplayName();
    }

    public void setAcceptedAnswetID(Integer acceptedAnswetID) {
        this.acceptedAnswetID = acceptedAnswetID;}
    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (userID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(userID);
        }
        parcel.writeString(profileImage1);
        parcel.writeString(Answeredname);
        if (acceptedAnswetID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(acceptedAnswetID);
        }
        if (answerCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(answerCount);
        }
        if (questionID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(questionID);
        }
        parcel.writeString(title);
    }
}
