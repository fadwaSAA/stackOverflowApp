package com.example.fadwasa.stackoverflowapp.Questions;

import com.example.fadwasa.stackoverflowapp.baseMVP.BaseModel;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QuestionInfo;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOutput;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QItem;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QOutput;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class QuestionsModel extends BaseModel implements QuestionsActivityMVP.Model {

        private QuestionInfo questionInfo;
        private UserAnsweredInfo userAnsweredInfo;
        private List<QItem> qItemList;
        private List<AItem> aItemList;
        private long timestamp;
        Observable<QItem>qItemObservable;
        boolean questionsSame;
        private static final long STALE_MS = 20 * 1000; // Data is stale after 20 seconds

        public QuestionsModel(QuestionInfo questionInfo, UserAnsweredInfo userAnsweredInfo) {
            this.timestamp = System.currentTimeMillis();
            this.questionInfo = questionInfo;
            this.userAnsweredInfo=userAnsweredInfo;
            qItemList = new ArrayList<>();
            aItemList = new ArrayList<>();
         }




    @Override
    public Observable<QItem> getResultsFromMemory(String userID) {
        if (isUpToDate(timestamp)&&qItemList.size() != 0) {
            if (qItemList.get(0).getOwner().getUserId()+"" == userID) {
                questionsSame = true;
                return Observable.fromIterable(qItemList);
            }
            else {
                questionsSame=false;
                timestamp = System.currentTimeMillis();
                qItemList.clear();
                return Observable.empty();
            }
        } else {
            questionsSame=false;
            timestamp = System.currentTimeMillis();
            qItemList.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<QItem> getResultsFromNetwork(String userID) {
        Observable<QOutput> qOutputObservable = questionInfo.getQuestionsInfo(userID+"","desc","activity");
        qItemObservable= qOutputObservable.concatMap(new Function<QOutput, Observable<QItem>>(){
            @Override
            public Observable<QItem> apply(QOutput qOutput) throws Exception {
                return Observable.fromIterable(qOutput.getItems());
            }
        }).doOnNext(new Consumer<QItem>() {
            @Override
            public void accept(QItem qItem) throws Exception {
                qItemList.add(qItem);
            }
        });
        return qItemObservable;
    }
    @Override
    public Observable<AItem> getAcceptedFromMemory() {
        if (isUpToDate(timestamp)) {
            if(questionsSame)
                return Observable.fromIterable(aItemList);
            else {
                timestamp = System.currentTimeMillis();
                aItemList.clear();
                return Observable.empty();
            }
        } else {
            timestamp = System.currentTimeMillis();
            aItemList.clear();
            return Observable.empty();
        }}



    @Override
    public Observable<AItem> getAcceptedFromNetwork(String userID) {
        return qItemObservable.concatMap(new Function<QItem, Observable<AOutput>>() {
            @Override
            public Observable<AOutput> apply(QItem qItem) throws Exception {
                return userAnsweredInfo.getAnsweredInfo(qItem.getQuestionId()+"","desc","activity");
            }
        }).concatMap(new Function<AOutput, Observable<AItem>>() {
            @Override
            public Observable<AItem> apply(AOutput aOutput) throws Exception {
                 AItem AcceptedItem= new AItem() ;
                for( int i =0;i< aOutput.getItems().size();i++) {
                    if(aOutput.getItems().get(i).getIsAccepted()) {
                        AcceptedItem = aOutput.getItems().get(i);
                        break;
                    }
                }
                 return Observable.just(AcceptedItem);
            }
        }).doOnNext(new Consumer<AItem>() {
            @Override
            public void accept(AItem aItem) throws Exception {
                aItemList.add(aItem);
            }
        });
    }

    @Override
    public Observable<AItem> getAcceptedData(String accountID) {
        return getAcceptedFromMemory().switchIfEmpty(getAcceptedFromNetwork(accountID));
    }

    @Override
    public Observable<QItem> getResultData(String userID) {
        return getResultsFromMemory(userID).switchIfEmpty(getResultsFromNetwork( userID));
    }


}
