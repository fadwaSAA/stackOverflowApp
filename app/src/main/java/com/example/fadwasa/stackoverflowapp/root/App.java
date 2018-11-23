package com.example.fadwasa.stackoverflowapp.root;

import android.app.Application;

import com.example.fadwasa.stackoverflowapp.Questions.QuestionsInfoModule;
import com.example.fadwasa.stackoverflowapp.Users.UsersInfoModule;
import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.usersAnswered.UsersAnsweredInfoModule;


public class App extends Application {

    private ApplicationComponent component,qComponent,aComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                 .usersInfoModule(new UsersInfoModule())
                .apiModuleForInfo(new ApiModuleForInfo())
                .build();
        qComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .questionsInfoModule(new QuestionsInfoModule())
                .apiModuleForInfo(new ApiModuleForInfo())
                .build();
        aComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .usersAnsweredInfoModule(new UsersAnsweredInfoModule())
                .apiModuleForInfo(new ApiModuleForInfo())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
    public ApplicationComponent getQComponent() {
        return qComponent;
    }

}
