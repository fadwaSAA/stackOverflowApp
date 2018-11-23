package com.example.fadwasa.stackoverflowapp.root;

import com.example.fadwasa.stackoverflowapp.Questions.QuestionsActivity;
import com.example.fadwasa.stackoverflowapp.Questions.QuestionsInfoModule;
import com.example.fadwasa.stackoverflowapp.Users.UsersActivity;
import com.example.fadwasa.stackoverflowapp.Users.UsersInfoModule;
import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.usersAnswered.UsersAnsweredActivity;
import com.example.fadwasa.stackoverflowapp.usersAnswered.UsersAnsweredInfoModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,  ApiModuleForInfo.class,UsersInfoModule.class, QuestionsInfoModule.class,UsersAnsweredInfoModule.class})
public interface ApplicationComponent {

    void inject(UsersActivity target);
    void injectQ(QuestionsActivity target);
    void injectA(UsersAnsweredActivity target);



}
