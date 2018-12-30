package com.example.fadwasa.stackoverflowapp.usersAnswered;

import android.content.Context;

import com.example.fadwasa.stackoverflowapp.root.UserAnsweredScope;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersAnsweredInfoModule {
    Context context;
    public UsersAnsweredInfoModule(Context context) {
        this.context=context;
    }

    @UserAnsweredScope
    @Provides
    public AnsweredViewModel provideUsersActivityPresenter(UsersAnsweredRepository usersModel) {
        return new AnsweredViewModel(usersModel);
    }




}
