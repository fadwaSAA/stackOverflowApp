package com.example.fadwasa.stackoverflowapp.Users;

import com.example.fadwasa.stackoverflowapp.baseMVP.BaseActivityMVP;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.BadgeCounts;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Item;
import io.reactivex.Observable;

public interface UsersActivityMVP  {

    interface View extends BaseActivityMVP.View{

        void updateData(ViewModel viewModel);
    }

    interface Presenter extends BaseActivityMVP.Presenter{

        void loadData();

        void setView(UsersActivityMVP.View view);

        Observable<ViewModel> result();
    }

    interface Model {
        Observable<Item> getResultsFromMemory();
        Observable<Item> getResultsFromNetwork();
        Observable<BadgeCounts> getBadgeFromMemory();
        Observable<BadgeCounts> getBadgeFromNetwork();
        Observable<Item> getResultData();
        Observable<BadgeCounts> getBadgeData();
    }
}
