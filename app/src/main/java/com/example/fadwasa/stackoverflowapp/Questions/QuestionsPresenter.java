package com.example.fadwasa.stackoverflowapp.Questions;

import android.util.Log;

import com.example.fadwasa.stackoverflowapp.baseMVP.BasePresenter;
import com.example.fadwasa.stackoverflowapp.baseMVP.BaseView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class QuestionsPresenter extends BasePresenter implements QuestionsActivityMVP.Presenter {

    private QuestionsActivityMVP.View view;
    private Disposable subscription = null;
    private BaseView baseView = new BaseView();
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
        model.getResultsFromNetwork(accountID);
        return model.getFinalResult(accountID);
    }

}
