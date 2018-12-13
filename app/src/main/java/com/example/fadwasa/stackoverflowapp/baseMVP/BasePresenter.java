package com.example.fadwasa.stackoverflowapp.baseMVP;

import io.reactivex.disposables.Disposable;

public abstract class BasePresenter implements BaseActivityMVP.Presenter {

      public void rxUnsubscribe(Disposable subscription) {
        if (subscription != null) {
            if (!subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
}
