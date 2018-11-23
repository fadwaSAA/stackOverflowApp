package com.example.fadwasa.stackoverflowapp.Questions;

import com.example.fadwasa.stackoverflowapp.http.QuestionInfo;
import com.example.fadwasa.stackoverflowapp.http.UserAnsweredInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionsInfoModule {

    @Provides
    public QuestionsActivityMVP.Presenter provideUsersActivityPresenter(QuestionsActivityMVP.Model usersModel) {
        return new QuestionsPresenter(usersModel);
    }

    @Provides
    public QuestionsActivityMVP.Model provideTopUsersActivityModel(Repository repository) {
        return new QuestionsModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideRepo(QuestionInfo userApiService, UserAnsweredInfo userAnsweredInfo) {
        return new QuestionsRepository(userApiService, userAnsweredInfo);
    }


}
