package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class UsersAnsweredPresenter implements UsersAnsweredActivityMVP.Presenter {

    private UsersAnsweredActivityMVP.View view;
    private Disposable subscription = null;
    private UsersAnsweredActivityMVP.Model model;

    public UsersAnsweredPresenter(UsersAnsweredActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData(String questionID) {

        subscription =
                result(questionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AOwner>() {
                    @Override
                    public void onComplete() {
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (view != null) {
                            view.showSnackbar("Error getting users",null);
                        }
                    }

                    @Override
                    public void onNext(AOwner aOwner) {
                        if (view != null) {
                            ViewModel viewModel = new ViewModel(aOwner);
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
    public void setView(UsersAnsweredActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public Observable<AOwner> result(String questionID) {
        return   model.getAnsweredData(questionID);
    }
}
