package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;

import io.reactivex.Observable;


public class UsersAnsweredModel implements UsersAnsweredActivityMVP.Model {

    private Repository repository;

    public UsersAnsweredModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<AOwner> result(String questionID) {
        return   repository.getAnsweredData(questionID);
    }
}
