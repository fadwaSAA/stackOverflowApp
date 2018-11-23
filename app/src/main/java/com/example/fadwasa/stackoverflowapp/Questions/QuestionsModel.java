package com.example.fadwasa.stackoverflowapp.Questions;

import io.reactivex.Observable;


public class QuestionsModel implements QuestionsActivityMVP.Model {

    private Repository repository;

    public QuestionsModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result(String accountID) {

        return repository.getFinalResult(accountID);
    }


}
