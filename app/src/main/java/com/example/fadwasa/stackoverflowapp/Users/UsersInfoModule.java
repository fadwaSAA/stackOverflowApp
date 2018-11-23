package com.example.fadwasa.stackoverflowapp.Users;

import com.example.fadwasa.stackoverflowapp.http.UserInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersInfoModule {

    @Provides
    public UsersActivityMVP.Presenter provideUsersActivityPresenter(UsersActivityMVP.Model usersModel) {
        return new UsersPresenter(usersModel);
    }

    @Provides
    public UsersActivityMVP.Model provideTopUsersActivityModel(Repository repository) {
        return new UsersModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideRepo(UserInfo userApiService) {
        return new UsersRepository(userApiService );
    }


}
