package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.root.ApplicationComponent;
import com.example.fadwasa.stackoverflowapp.root.UserAnsweredScope;
import dagger.Component;

@Component(dependencies =  ApplicationComponent.class, modules = { ApiModuleForInfo.class ,UsersAnsweredInfoModule.class})
@UserAnsweredScope
public interface AnswerApplicationComponent {
    void injectA(UsersAnsweredActivity target);

}

