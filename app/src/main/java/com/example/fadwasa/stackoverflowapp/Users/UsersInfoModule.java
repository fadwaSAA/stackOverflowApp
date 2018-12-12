package com.example.fadwasa.stackoverflowapp.Users;

import android.content.Context;

import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.UserInfo;
import com.example.fadwasa.stackoverflowapp.root.App;
import com.example.fadwasa.stackoverflowapp.root.UserScope;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersInfoModule {

    Context context;
    public UsersInfoModule(Context context) {
        this.context=context;
    }

    @Provides
     public UsersActivityMVP.Presenter provideUsersActivityPresenter(UsersActivityMVP.Model usersModel) {
        return new UsersPresenter(usersModel);
    }


    @UserScope
    @Provides
    public UsersActivityMVP.Model provideTopUsersActivityModel(UserInfo userApiService) {
        return new UsersModel(userApiService);
    }

}
