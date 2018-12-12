package com.example.fadwasa.stackoverflowapp.root;

import android.app.Application;


import com.example.fadwasa.stackoverflowapp.Questions.DaggerQuestionApplicationComponent;
import com.example.fadwasa.stackoverflowapp.Questions.QuestionApplicationComponent;
import com.example.fadwasa.stackoverflowapp.Questions.QuestionsInfoModule;
import com.example.fadwasa.stackoverflowapp.Users.DaggerUserApplicationComponent;
import com.example.fadwasa.stackoverflowapp.Users.UserApplicationComponent;
import com.example.fadwasa.stackoverflowapp.Users.UsersInfoModule;
import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.usersAnswered.AnswerApplicationComponent;
import com.example.fadwasa.stackoverflowapp.usersAnswered.DaggerAnswerApplicationComponent;
import com.example.fadwasa.stackoverflowapp.usersAnswered.UsersAnsweredInfoModule;


public class App extends Application {
    ApplicationComponent component;

    private UserApplicationComponent uComponent;
    private AnswerApplicationComponent aComponent;
    private QuestionApplicationComponent qComponent;




    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                 .apiModuleForInfo(new ApiModuleForInfo())
                .build();
        uComponent = DaggerUserApplicationComponent.builder()
                .applicationComponent(component)
                .usersInfoModule(new UsersInfoModule(this))
                .build();
        aComponent = DaggerAnswerApplicationComponent.builder()
                .applicationComponent(component)
                .usersAnsweredInfoModule(new UsersAnsweredInfoModule(this))
                .build();
        qComponent = DaggerQuestionApplicationComponent.builder()
                .applicationComponent(component)
                .questionsInfoModule(new QuestionsInfoModule(this))
                .build();

    }

    public ApplicationComponent getComponent() {
        return component;
    }
    public UserApplicationComponent getUserApplicationComponent() {
        return uComponent;
    }


    public AnswerApplicationComponent getAnsweredComponenet() {
        return aComponent;
    }


    public QuestionApplicationComponent getQComponent() {
        return qComponent;
    }
}
