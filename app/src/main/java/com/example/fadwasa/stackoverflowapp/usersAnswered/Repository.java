package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.http.apimodel.AItem;
import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;
import io.reactivex.Observable;

public interface Repository {

    Observable<AItem> getResultsFromMemory(String questionID);
    Observable<AItem> getResultsFromNetwork(String questionID);
    Observable<AOwner> getAnsweredFromMemory(String questionID);
    Observable<AOwner> getAnsweredFromNetwork(String questionID);
    Observable<AItem> getResultData(String questionID);
    Observable<AOwner> getAnsweredData(String questionID);

}
