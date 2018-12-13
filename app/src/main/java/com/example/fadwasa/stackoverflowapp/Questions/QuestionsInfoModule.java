package com.example.fadwasa.stackoverflowapp.Questions;

import android.content.Context;

import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QuestionInfo;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.root.QuestionScope;
import dagger.Module;
import dagger.Provides;

@Module
public class QuestionsInfoModule {
    Context context;
    public QuestionsInfoModule(Context context) {
        this.context=context;
    }

    @Provides
    public QuestionsActivityMVP.Presenter provideUsersActivityPresenter(QuestionsActivityMVP.Model usersModel) {
        return new QuestionsPresenter(usersModel);
    }
@QuestionScope
    @Provides
    public QuestionsActivityMVP.Model provideTopUsersActivityModel(QuestionInfo userApiService, UserAnsweredInfo userAnsweredInfo) {
        return new QuestionsModel(userApiService,userAnsweredInfo);
    }
}
