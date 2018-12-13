package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.baseMVP.BaseModel;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOutput;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class UsersAnsweredModel extends BaseModel implements UsersAnsweredActivityMVP.Model {

    private UserAnsweredInfo userInfo;
    private List<AItem> results;
    private long timestamp;
    private List<AOwner> owners;
    private boolean sameID=false;
    private static final long STALE_MS = 20 * 1000; // Data is stale after 20 seconds

    public UsersAnsweredModel(UserAnsweredInfo userInfo ) {
         this.timestamp = System.currentTimeMillis();
        this.userInfo = userInfo;
        owners = new ArrayList<>();
        results = new ArrayList<>();
    }

    @Override
    public Observable<AItem> getResultsFromMemory(String questionID) {
        if (isUpToDate(timestamp)&&results.size() != 0) {
            if (results.get(0).getQuestionId()+"" == questionID) {
                sameID=true;
                return Observable.fromIterable(results);
            }
            else {
                sameID=false;
                timestamp = System.currentTimeMillis();
                results.clear();
                return Observable.empty();
            }
        } else {
            sameID=false;
            timestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<AOwner> getAnsweredFromMemory(String questionID) {
        getResultsFromMemory(questionID);
        if (isUpToDate(timestamp)&&owners.size()!=0) {
            if(sameID)
                return Observable.fromIterable(owners);
            else {
                timestamp = System.currentTimeMillis();
                owners.clear();
                return Observable.empty();
            }
        } else {
            timestamp = System.currentTimeMillis();
            owners.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<AItem> getResultsFromNetwork(String questionID) {

        Observable<AOutput> usersObservable = userInfo.getAnsweredInfo(questionID,"desc","activity");

        return usersObservable.concatMap(new Function<AOutput, Observable<AItem>>() {
            @Override
            public Observable<AItem> apply(AOutput output) {
                return Observable.fromIterable(output.getItems());
            }
        }).doOnNext(new Consumer<AItem>() {
            @Override
            public void accept(AItem result) {
                results.add(result);
            }
        });
    }


    @Override
    public Observable<AOwner> getAnsweredFromNetwork(String questionID) {

        return getResultsFromNetwork(questionID)
                .concatMap(new Function<AItem, Observable<AOwner>>() {
                    @Override
                    public Observable<AOwner> apply(AItem result) {
                        return Observable.just(result.getOwner());
                    }
                }) .doOnNext(new Consumer<AOwner>() {
                    @Override
                    public void accept(AOwner s) {
                        owners.add(s);
                    }
                });
    }


    @Override
    public Observable<AItem> getResultData(String questionID) {
        return getResultsFromMemory(questionID).switchIfEmpty(getResultsFromNetwork(questionID));
    }
    @Override
    public Observable<AOwner> getAnsweredData(String questionID) {
        return getAnsweredFromMemory(questionID).switchIfEmpty(getAnsweredFromNetwork(questionID));
    }
}

