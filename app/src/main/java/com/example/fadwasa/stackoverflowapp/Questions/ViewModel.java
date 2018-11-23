package com.example.fadwasa.stackoverflowapp.Questions;


import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;
import com.example.fadwasa.stackoverflowapp.http.apimodel.Owner;

public class ViewModel {
    private Integer userID;
    private String profileImage1;
    private String Answeredname;

    private Integer acceptedAnswetID;
    private Integer answerCount;
    private Integer questionID;
    private String title;
    private Owner owner;




    public ViewModel(Integer acceptedAnswetID, Integer answerCount, Integer questionID, String tile, Owner owner, AOwner answer) {
         this.acceptedAnswetID=acceptedAnswetID;
         this.answerCount=answerCount;
         this.questionID=questionID;
         this.title=tile;
         this.owner=owner;
         this.profileImage1=answer.getProfileImage();
         this.Answeredname=answer.getDisplayName();



    }
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
        this.acceptedAnswetID = acceptedAnswetID;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
