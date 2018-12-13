package com.example.fadwasa.stackoverflowapp.Questions;

import android.util.Log;

import com.example.fadwasa.stackoverflowapp.baseMVP.BasePresenter;
import com.example.fadwasa.stackoverflowapp.baseMVP.BaseView;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QItem;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class QuestionsPresenter extends BasePresenter implements QuestionsActivityMVP.Presenter {

    private QuestionsActivityMVP.View view;
    private Disposable subscription = null;
     private QuestionsActivityMVP.Model model;
    public QuestionsPresenter(QuestionsActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData(String accountID) {

        subscription = result(accountID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ViewModel>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        view.ShowProgressBar();
                    }

                    @Override
                    public void onComplete() {

                        view.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.hideProgressBar();

                        if (view != null) {
                            view.showSnackbar("Error getting questions",null);
                        }
                    }

                    @Override
                    public void onNext(ViewModel viewModel) {
                        if (view != null) {
                            view.updateData(viewModel);
                        }
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        super.rxUnsubscribe(subscription);

    }

    @Override
    public void setView(QuestionsActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public Observable<ViewModel> result(String accountID) {

        return Observable.zip(
                model.getResultData(accountID),
                model.getAcceptedData(accountID),

                new BiFunction<QItem, AItem, ViewModel>() {
                    @Override
                    public ViewModel apply(QItem result, AItem aItem) {
                        if(aItem.getOwner()!=null)

                        return new ViewModel(result.getAcceptedAnswerId(),result.getAnswerCount(),result.getQuestionId(),result.getTitle(),result.getOwner(),aItem.getOwner());
                        else
                        return new ViewModel(result.getAcceptedAnswerId(),result.getAnswerCount(),result.getQuestionId(),result.getTitle(),result.getOwner());

                    }
                }
        );    }

}
