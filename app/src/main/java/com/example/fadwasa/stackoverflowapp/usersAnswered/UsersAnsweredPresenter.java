package com.example.fadwasa.stackoverflowapp.usersAnswered;

import com.example.fadwasa.stackoverflowapp.baseMVP.BasePresenter;
import com.example.fadwasa.stackoverflowapp.baseMVP.BaseView;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class UsersAnsweredPresenter extends BasePresenter implements UsersAnsweredActivityMVP.Presenter {

    private UsersAnsweredActivityMVP.View view;
    private Disposable subscription = null;
    private UsersAnsweredActivityMVP.Model model;
    private BaseView baseView = new BaseView();


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
                        view.hideProgressBar();
                        e.printStackTrace();
                        if (view != null) {
                            view.showSnackbar("Error getting users",null);
                        }
                    }

                    @Override
                    public void onNext(AOwner viewModel) {
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
    public void setView(UsersAnsweredActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public Observable<AOwner> result(String questionID) {
        return   model.getAnsweredData(questionID);
    }

}
