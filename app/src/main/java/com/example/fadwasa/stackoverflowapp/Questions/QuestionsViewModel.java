package com.example.fadwasa.stackoverflowapp.Questions;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.Owner;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QuestionInfo;
import java.util.List;



public class QuestionsViewModel extends ViewModel{
    private Integer userID;
    private String profileImage1;
    private String Answeredname;
    private Integer acceptedAnswetID;
    private Integer answerCount;
    private Integer questionID;
    private String title;
    private Owner owner;
    private LiveData<List<QuestionsViewModel>> questionsLiveData;
    private QuestionsRepository repository;


    public QuestionsViewModel(){

    }
    public QuestionsViewModel(QuestionsRepository repository) {
        this.repository=repository;
    }

    public QuestionsViewModel(Integer acceptedAnswetID, Integer answerCount, Integer questionID, String title, Owner owner, AOwner aOwner) {

        this.acceptedAnswetID=acceptedAnswetID;
        this.answerCount=answerCount;
        this.questionID=questionID;
        this.title=title;
        this.owner=owner;
        this.profileImage1 = aOwner.getProfileImage();
        this.Answeredname = aOwner.getDisplayName();
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




    public LiveData<List<QuestionsViewModel>> loadData(String accountID) {

       if(questionsLiveData==null){
           QuestionInfo questionInfo = new ApiModuleForInfo().provideApiServiceQ();
           UserAnsweredInfo userAnsweredInfo= new ApiModuleForInfo().provideApiServiceA();
           repository=new QuestionsRepository(questionInfo,userAnsweredInfo);
           questionsLiveData = repository.getFinalResult(accountID);

       }
       return questionsLiveData;
    }

}
