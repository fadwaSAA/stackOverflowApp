package com.example.fadwasa.stackoverflowapp.Users;

import com.example.fadwasa.stackoverflowapp.baseMVP.BasePresenter;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.BadgeCounts;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Item;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UsersPresenter extends BasePresenter implements UsersActivityMVP.Presenter {

    private UsersActivityMVP.View view;
     private Disposable subscription = null;
    private UsersActivityMVP.Model model;

    public UsersPresenter(UsersActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData() {

        subscription = result()
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
                            view.showSnackbar("Error getting users",null);
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
    public void setView(UsersActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(
                model.getResultData(),
                model.getBadgeData(),
                new BiFunction<Item, BadgeCounts, ViewModel>() {
                    @Override
                    public ViewModel apply(Item result, BadgeCounts s) {
                        return new ViewModel(result.getDisplayName(),result.getProfileImage(),result.getAccountId(), s);
                    }
                }
        );
    }


}
