package com.example.fadwasa.stackoverflowapp.Questions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class QuestionsPresenter implements QuestionsActivityMVP.Presenter {

    private QuestionsActivityMVP.View view;
    private Disposable subscription = null;
    private QuestionsActivityMVP.Model model;

    public QuestionsPresenter(QuestionsActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData(String accountID) {

        subscription = model
                .result(accountID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ViewModel>() {
                    @Override
                    public void onComplete() {
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (view != null) {
                            view.showSnackbar("Error getting questions");
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
        if (subscription != null) {
            if (!subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    @Override
    public void setView(QuestionsActivityMVP.View view) {
        this.view = view;
    }

}
