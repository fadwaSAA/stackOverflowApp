package com.example.fadwasa.stackoverflowapp.Questions;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;
  import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOutput;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QItem;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QOutput;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QuestionInfo;
import java.util.ArrayList;
import java.util.List;
 import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionsRepository  {
     private QuestionInfo questionInfo;
     private UserAnsweredInfo userAnsweredInfo;
     private MediatorLiveData<List<QuestionsViewModel>> questionsViewModelMediatorLiveData;
     MutableLiveData<List<QItem>> qItemLiveData;
     List<QuestionsViewModel> questionsViewModelList;
     LiveData<List<AItem>> aItemLiveData;
     MutableLiveData<List<AOutput>> aOutputLiveDataTemp;
     MutableLiveData <List<QuestionsViewModel>> questionsViewModelLivedata;
     MutableLiveData<List<AItem>>aItemLiveDataF;
     private int counter;
     QItem qItem1;


    public QuestionsRepository(QuestionInfo questionInfo, UserAnsweredInfo userAnsweredInfo) {
         this.questionInfo = questionInfo;
        this.userAnsweredInfo=userAnsweredInfo;
        questionsViewModelList =new ArrayList<>();
        qItemLiveData = new MutableLiveData<>();
        aItemLiveData=new MutableLiveData<>();
        aOutputLiveDataTemp=new MutableLiveData<>();
        questionsViewModelLivedata=new MutableLiveData<>();
        aItemLiveDataF = new MutableLiveData<>();
    }






    public LiveData<List<QItem>> getResultsFromNetwork(String userID) {

        questionInfo.getQuestionsInfo(userID+"","desc","activity").enqueue(new Callback<QOutput>() {
            @Override
            public void onResponse(Call<QOutput> call, Response<QOutput> response) {
                if(response.isSuccessful()) {
                    qItemLiveData.setValue(response.body().getItems());

                }
            }

            @Override
            public void onFailure(Call<QOutput> call, Throwable t) {

                qItemLiveData.setValue(null);

            }
        });

        return qItemLiveData;
    }



    public AItem getAcceptedAnswer( List<AItem> answersLiveData) {
        AItem item=new AItem();
        for(int i =0; i<answersLiveData.size();i++){
            if(answersLiveData.get(i).getIsAccepted()) {
                item = answersLiveData.get(i);
                return item;
            }
        }
        return item;
    }

    public LiveData<AItem> getAcceptedFromNetwork(QItem qItem) {
        final QItem qItem2=qItem;
       final MutableLiveData<AItem>aItemLiveData=new MutableLiveData();

        userAnsweredInfo.getAnsweredInfo(qItem2.getQuestionId()+"" , "desc", "activity").enqueue(new Callback<AOutput>() {
            @Override
            public void onResponse(Call<AOutput> call, Response<AOutput> response) {
                if (response.isSuccessful()) {
                     aItemLiveData.setValue(getAcceptedAnswer(response.body().getItems()));
                    questionsViewModelList.add(new QuestionsViewModel(qItem2.getAcceptedAnswerId(), qItem2.getAnswerCount(), qItem2.getQuestionId(), qItem2.getTitle(), qItem2.getOwner(),aItemLiveData.getValue().getOwner()));
                }
            }

            @Override
            public void onFailure(Call<AOutput> call, Throwable t) {
                aItemLiveData.setValue(null);

            }
        });

        return aItemLiveData;
    }

    public LiveData<List<QuestionsViewModel>>getFinalResult(String userID){
        LiveData<List<QItem>>qItemLiveData=getResultsFromNetwork(userID);
        LiveData<List<QuestionsViewModel>>questionsLivedata=Transformations.switchMap(qItemLiveData, new android.arch.core.util.Function<List<QItem>, LiveData<List<QuestionsViewModel>>>() {
            @Override
            public LiveData<List<QuestionsViewModel>> apply(final List<QItem> qItems) {
                counter=0;
                questionsViewModelMediatorLiveData = new MediatorLiveData<>();
                for (QItem qItem : qItems) {

                    qItem1=qItem;
                    questionsViewModelMediatorLiveData.addSource(getAcceptedFromNetwork(qItem1), new Observer<AItem>() {
                        @Override
                        public void onChanged(@Nullable AItem aItem1) {
                             counter++;

                            if(counter==qItems.size()-1)
                            questionsViewModelMediatorLiveData.postValue(questionsViewModelList);
                        }
                    });


                }

                return questionsViewModelMediatorLiveData;
            }
        });
        return questionsLivedata;

    }
}







