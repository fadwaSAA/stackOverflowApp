package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.http.UserAnsweredInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersAnsweredInfoModule {

    @Provides
    public UsersAnsweredActivityMVP.Presenter provideUsersActivityPresenter(UsersAnsweredActivityMVP.Model usersModel) {
        return new UsersAnsweredPresenter(usersModel);
    }

    @Provides
    public UsersAnsweredActivityMVP.Model provideTopUsersActivityModel(Repository repository) {
        return new UsersAnsweredModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideRepo(UserAnsweredInfo userApiService) {
        return new UsersAnsweredRepository(userApiService );
    }


}
