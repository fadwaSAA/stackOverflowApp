package com.example.fadwasa.stackoverflowapp.baseMVP;

import android.view.ViewGroup;

import com.example.fadwasa.stackoverflowapp.Questions.QuestionsActivityMVP;
import com.example.fadwasa.stackoverflowapp.Questions.ViewModel;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QItem;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Fadwasa on 06/12/2018 AD.
 */

public interface BaseActivityMVP {

    interface View {
         void showSnackbar(String s,ViewGroup rootView);

        void ShowProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void rxUnsubscribe();
    }
}
