package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;

import io.reactivex.Observable;

public interface UsersAnsweredActivityMVP {

    interface View {

        void updateData(AOwner viewModel);

        void showSnackbar(String s);

    }

    interface Presenter {

        void loadData(String questionID);

        void rxUnsubscribe();

        void setView(UsersAnsweredActivityMVP.View view);

    }

    interface Model {

        Observable<AOwner> result(String questionID);

    }
}
