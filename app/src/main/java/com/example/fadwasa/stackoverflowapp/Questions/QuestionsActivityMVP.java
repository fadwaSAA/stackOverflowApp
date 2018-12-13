package com.example.fadwasa.stackoverflowapp.Questions;


import com.example.fadwasa.stackoverflowapp.baseMVP.BaseActivityMVP;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QItem;
import io.reactivex.Observable;

public interface QuestionsActivityMVP {

    interface View extends BaseActivityMVP.View {

        void updateData(ViewModel viewModel);
    }

    interface Presenter extends BaseActivityMVP.Presenter{

        void loadData(String accountID);
        void setView(QuestionsActivityMVP.View view);
        Observable<ViewModel> result(String accountID);
    }

    interface Model {
        Observable<QItem>  getResultsFromMemory(String userID);

        Observable<QItem> getResultsFromNetwork(final String userID);
          Observable<AItem> getAcceptedFromMemory();
          Observable<AItem> getAcceptedFromNetwork(String userID);
          Observable<AItem> getAcceptedData(String accountID);
        Observable<QItem> getResultData(String userID);

    }
}
