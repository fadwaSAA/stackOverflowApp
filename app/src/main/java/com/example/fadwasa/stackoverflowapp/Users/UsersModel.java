package com.example.fadwasa.stackoverflowapp.Users;

import com.example.fadwasa.stackoverflowapp.http.apimodel.BadgeCounts;
import com.example.fadwasa.stackoverflowapp.http.apimodel.Item;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;


public class UsersModel implements UsersActivityMVP.Model {

    private Repository repository;

    public UsersModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(
                repository.getResultData(),
                repository.getBadgeData(),
                new BiFunction<Item, BadgeCounts, ViewModel>() {
                    @Override
                    public ViewModel apply(Item result, BadgeCounts s) {
                        return new ViewModel(result.getDisplayName(),result.getProfileImage(),result.getAccountId(), s);
                    }
                }
        );
    }

}
