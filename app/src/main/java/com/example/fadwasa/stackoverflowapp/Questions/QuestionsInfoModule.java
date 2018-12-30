package com.example.fadwasa.stackoverflowapp.Questions;

import android.content.Context;
import com.example.fadwasa.stackoverflowapp.root.QuestionScope;
import dagger.Module;
import dagger.Provides;


@Module
public class QuestionsInfoModule {
    Context context;
    public QuestionsInfoModule(Context context) {
        this.context=context;
    }



    @QuestionScope
    @Provides
    public QuestionsViewModel provideUsersActivityPresenter(QuestionsRepository usersModel) {
        return new QuestionsViewModel(usersModel);
    }

}
