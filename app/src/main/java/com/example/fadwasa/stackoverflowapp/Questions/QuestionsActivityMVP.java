package com.example.fadwasa.stackoverflowapp.Questions;


import io.reactivex.Observable;

public interface QuestionsActivityMVP {

    interface View {

        void updateData(ViewModel viewModel);

        void showSnackbar(String s);

    }

    interface Presenter {

        void loadData(String accountID);

        void rxUnsubscribe();

        void setView(QuestionsActivityMVP.View view);

    }

    interface Model {

        Observable<ViewModel> result(String accountID);

    }
}
