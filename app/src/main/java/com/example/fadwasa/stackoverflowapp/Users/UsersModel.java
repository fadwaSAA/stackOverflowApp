package com.example.fadwasa.stackoverflowapp.Users;


import com.example.fadwasa.stackoverflowapp.baseMVP.BaseModel;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.UserInfo;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.BadgeCounts;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Item;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Output;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class UsersModel extends BaseModel implements UsersActivityMVP.Model  {

     private UserInfo userInfo;
    private List<Item> results;
    private List<BadgeCounts> badges;
    private long timestamp;


    public UsersModel(UserInfo userInfo ) {
         this.timestamp = System.currentTimeMillis();
        this.userInfo = userInfo;
        badges = new ArrayList<>();
        results = new ArrayList<>();
    }


    @Override
    public Observable<Item> getResultsFromMemory() {
        if (isUpToDate(timestamp)) {
            return Observable.fromIterable(results);
        } else {
            timestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<BadgeCounts> getBadgeFromMemory() {
        if (isUpToDate(timestamp)) {
            return Observable.fromIterable(badges);
        } else {
            timestamp = System.currentTimeMillis();
            badges.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Item> getResultsFromNetwork() {

        Observable<Output> usersObservable = userInfo.getUsersInfo("desc","reputation");

        return usersObservable.concatMap(new Function<Output, Observable<Item>>() {
            @Override
            public Observable<Item> apply(Output users) {
                return Observable.fromIterable(users.getItems());
            }
        }).doOnNext(new Consumer<Item>() {
            @Override
            public void accept(Item result) {
                results.add(result);
            }
        });
    }


    @Override
    public Observable<BadgeCounts> getBadgeFromNetwork() {

        return getResultsFromNetwork()
                .concatMap(new Function<Item, Observable<BadgeCounts>>() {
                    @Override
                    public Observable<BadgeCounts> apply(Item result) {
                        return Observable.just(result.getBadgeCounts());
                    }
                }) .doOnNext(new Consumer<BadgeCounts>() {
                    @Override
                    public void accept(BadgeCounts s) {
                        badges.add(s);
                    }
                });
    }


    @Override
    public Observable<Item> getResultData() {
        return getResultsFromMemory().switchIfEmpty(getResultsFromNetwork());
    }
    @Override
    public Observable<BadgeCounts> getBadgeData() {
        return getBadgeFromMemory().switchIfEmpty(getBadgeFromNetwork());
    }
}

