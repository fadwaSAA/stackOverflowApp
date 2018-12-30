package com.example.fadwasa.stackoverflowapp.Questions;


import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.root.ApplicationComponent;
import com.example.fadwasa.stackoverflowapp.root.QuestionScope;

import dagger.Component;


@Component(dependencies =  ApplicationComponent.class,modules = {ApiModuleForInfo.class, QuestionsInfoModule.class })
@QuestionScope

public interface QuestionApplicationComponent {

     void injectQ(QuestionsActivity target);
}

