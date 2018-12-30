package com.example.fadwasa.stackoverflowapp.Users;

import android.content.Context;

import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.UserInfo;
import com.example.fadwasa.stackoverflowapp.root.UserScope;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersInfoModule {

    Context context;
    public UsersInfoModule(Context context) {
        this.context=context;
    }
    @UserScope
    @Provides
     public UsersViewModel provideUsersActivityPresenter(UsersRpository usersModel) {
        return new UsersViewModel(usersModel);
    }
/*
    @UserScope
    @Provides
    public UsersViewModel provideTopUsersActivityModel(UserInfo userApiService) {
        return new UsersModel();
    }
*/
}
