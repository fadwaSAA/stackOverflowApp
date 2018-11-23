package com.example.fadwasa.stackoverflowapp.Users;

import com.example.fadwasa.stackoverflowapp.http.apimodel.BadgeCounts;
import com.example.fadwasa.stackoverflowapp.http.apimodel.Item;
import io.reactivex.Observable;

public interface Repository {

    Observable<Item> getResultsFromMemory();

    Observable<Item> getResultsFromNetwork();

    Observable<BadgeCounts> getBadgeFromMemory();
    Observable<BadgeCounts> getBadgeFromNetwork();
    Observable<Item> getResultData();
    Observable<BadgeCounts> getBadgeData();

}
