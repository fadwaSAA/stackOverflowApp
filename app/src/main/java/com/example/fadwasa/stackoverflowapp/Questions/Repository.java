package com.example.fadwasa.stackoverflowapp.Questions;


import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;
import com.example.fadwasa.stackoverflowapp.http.apimodel.QItem;

import io.reactivex.Observable;


public interface Repository {

    Observable<QItem>  getResultsFromMemory(String userID);

    Observable<QItem>  getResultsFromNetwork(final String userID);

    Observable<AOwner> getAcceptedFromMemory();
    Observable<AOwner> getAcceptedFromNetwork(final String questionID);
    Observable<QItem> getResultData(String userID);
    Observable<AOwner> getAcceptedData(String questionID);
      Observable<ViewModel> getFinalResult(String accountID);

}
