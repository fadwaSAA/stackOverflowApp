package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.baseMVP.BaseActivityMVP;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;

import io.reactivex.Observable;

public interface UsersAnsweredActivityMVP {

    interface View extends BaseActivityMVP.View{

        void updateData(AOwner viewModel);


    }

    interface Presenter extends BaseActivityMVP.Presenter{

        void loadData(String questionID);
        void setView(UsersAnsweredActivityMVP.View view);
        Observable<AOwner> result(String questionID);

    }

    interface Model {

         Observable<AItem> getResultsFromMemory(String questionID);
        Observable<AItem> getResultsFromNetwork(String questionID);
        Observable<AOwner> getAnsweredFromMemory(String questionID);
        Observable<AOwner> getAnsweredFromNetwork(String questionID);
        Observable<AItem> getResultData(String questionID);
        Observable<AOwner> getAnsweredData(String questionID);


    }
}
