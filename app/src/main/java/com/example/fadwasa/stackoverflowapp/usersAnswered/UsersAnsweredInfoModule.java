package com.example.fadwasa.stackoverflowapp.usersAnswered;

import android.content.Context;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.root.UserAnsweredScope;
import dagger.Module;
import dagger.Provides;

@Module
public class UsersAnsweredInfoModule {
    Context context;
    public UsersAnsweredInfoModule(Context context) {
        this.context=context;
    }

    @Provides
    public UsersAnsweredActivityMVP.Presenter provideUsersActivityPresenter(UsersAnsweredActivityMVP.Model usersModel) {
        return new UsersAnsweredPresenter(usersModel);
    }
    @UserAnsweredScope
    @Provides
    public UsersAnsweredActivityMVP.Model provideTopUsersActivityModel(UserAnsweredInfo userApiService) {
        return new UsersAnsweredModel(userApiService);
    }



}
