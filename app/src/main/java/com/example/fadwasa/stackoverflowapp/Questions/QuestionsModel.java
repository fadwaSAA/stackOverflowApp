package com.example.fadwasa.stackoverflowapp.Questions;


import android.util.Log;


import com.example.fadwasa.stackoverflowapp.baseMVP.BaseModel;
import com.example.fadwasa.stackoverflowapp.http.QuestionsInfoPckge.QuestionInfo;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.UserAnsweredInfo;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOutput;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;
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
    private UserAnsweredInfo userInfo;
    //private MoreInfoApiService moreInfoApiService;
    //private List<String> countries;
    private List<QItem> results;
    private List<AItem> results1;
    boolean isAccepted;
    private long timestamp;
    private List<AOwner> owners;
    private boolean questionsSame = false;
    private String questionID;
    private static final long STALE_MS = 20 * 1000; // Data is stale after 20 seconds

    public QuestionsModel(QuestionInfo questionInfo, UserAnsweredInfo userInfo) {
        //this.moreInfoApiService = moreInfoApiService;
        this.timestamp = System.currentTimeMillis();
        this.questionInfo = questionInfo;
        this.userInfo=userInfo;
        owners = new ArrayList<>();
        results = new ArrayList<>();
        results1 = new ArrayList<>();

    }



    @Override
    public Observable<QItem> getResultsFromMemory(String userID) {
        if (isUpToDate(timestamp)&&results.size() != 0) {
            if (results.get(0).getOwner().getUserId()+"" == userID) {
                questionsSame = true;
                return Observable.fromIterable(results);
            }
            else {
                questionsSame=false;
                timestamp = System.currentTimeMillis();
                results.clear();
                return Observable.empty();
            }
        } else {
            questionsSame=false;
            timestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }


    @Override
    public Observable<QItem> getResultsFromNetwork(final String userID) {

        Log.d("vall","useridddd"+userID);
        Observable<QOutput> retrievedQuestions = questionInfo.getQuestionsInfo(userID+"","desc","activity");

        return retrievedQuestions.concatMap(new Function<QOutput, Observable<QItem>>() {
            @Override
            public Observable<QItem> apply(QOutput questions) {

                return Observable.fromIterable(questions.getItems());
            }
        }).doOnNext(new Consumer<QItem>() {
            @Override
            public void accept(QItem result) {

                results.add(result);


            }

        });
    }




    @Override
    public Observable<AItem> getAcceptedFromMemory() {
        if (isUpToDate(timestamp)) {
            if(questionsSame)
                return Observable.fromIterable(results1);
            else {
                timestamp = System.currentTimeMillis();
                results1.clear();
                return Observable.empty();
            }
        } else {
            timestamp = System.currentTimeMillis();
            results1.clear();
            return Observable.empty();
        }
    }




    @Override
    public Observable<AOwner> getAcceptedFromNetwork(String userID) {
        return getResultsFromNetwork1(userID)
                .concatMap(new Function<AItem, Observable<AOwner>>() {
                    @Override
                    public Observable<AOwner> apply(AItem result) {
                        if(result==null)
                            return Observable.just(null);
                        else
                        return Observable.just(result.getOwner());
                    }
                }) .doOnNext(new Consumer<AOwner>() {
                    @Override
                    public void accept(AOwner s) {

                        owners.add(s);
                    }
                });
    }

   /* public Observable<AItem> getResultsFromNetwork1(String userID) {
        Observable<List<AItem>> aItemObservable = getResultsFromNetwork2(userID);
        return aItemObservable.concatMap(new Function<List<AItem>, Observable<AItem>>() {
            @Override
            public Observable<AItem> apply(List<AItem> aItems) throws Exception {
                //repoMemory.setRepos(repos);
                return Observable.fromIterable(aItems);
            }
        }).doOnNext(new Consumer<AItem>() {
            @Override
            public void accept(AItem result) {
                results1.add(result);
            }
        });
    }
    */


    public Observable<AItem> getResultsFromNetwork1(String userID) {


        /*return getResultsFromNetwork(userID).concatMap(new Function<QItem, Observable<AOutput>>() {

            @Override
            public Observable<AOutput> apply(QItem output) {

                Log.d("output","is88"+output.getQuestionId());
                Log.d("userInfo","is0000"+userInfo);
                 if(output.getAcceptedAnswerId()==null)
                   results1.add(null);
                return userInfo.getAnsweredInfo(output.getQuestionId()+"","desc","activity");
            }
        }). concatMap(new Function<AOutput, Observable<AItem>>() {

            @Override
            public Observable<AItem> apply(AOutput output) throws Exception {


                return Observable.fromIterable(output.getItems());//list of ans
            }
        }).doOnNext(new Consumer<AItem>() {
            @Override
            public void accept(AItem result) {

                if(result.getIsAccepted()){
                     results1.add(result);
                }
            }
        });
        */

        return getResultsFromNetwork(userID).concatMap(new Function<QItem, Observable<AOutput>>() {
                @Override
                public Observable<AOutput> apply(QItem result) {

                    Log.d("$4444","4444"+result.getQuestionId());
                    return userInfo.getAnsweredInfo(result.getQuestionId()+"","desc","activity");
                }
            }).concatMap(new Function<AOutput, Observable<AItem>>() {
                @Override
                public Observable<AItem> apply(AOutput aOutput) {
                    Log.d("$4444","444411"+aOutput.getItems());
                    Log.d("$4444","444422"+aOutput.getItems().size());
                    List<AItem> AcceptedList = new ArrayList<>();
                   if(aOutput.getItems().size()==0)
                       AcceptedList.add(null);
                    //return Observable.fromIterable(aOutput.getItems());//list of ans
                    for(int i =0; i<aOutput.getItems().size();i++)
                    {
                        if(aOutput.getItems().get(i).getIsAccepted())
                            AcceptedList.add(aOutput.getItems().get(i));
                    }
                   // return Observable.just(AcceptedList);
                    return Observable.fromIterable(AcceptedList);//list of ans
                }
            }).doOnNext(new Consumer<AItem>() {
            @Override
                 public void accept(AItem aItem) throws Exception {

                   if(aItem.getIsAccepted()){
                         results1.add(aItem);
                     }
                 }

             });
    }


    @Override
    public Observable<QItem> getResultData(String userID) {
        return getResultsFromMemory(userID).switchIfEmpty(getResultsFromNetwork( userID));
    }
    @Override
    public Observable<AItem> getAcceptedData(String accountID) {
        return getAcceptedFromMemory().switchIfEmpty(getResultsFromNetwork1(accountID));
    }
    @Override
    public Observable<ViewModel> getFinalResult(String accountID) {
        return Observable.zip(
                getResultData(accountID),
                getAcceptedData(accountID),

                new BiFunction<QItem, AItem, ViewModel>() {
                    @Override
                    public ViewModel apply(QItem result, AItem s) {
                        if(s==null)
                            return new ViewModel(result.getAcceptedAnswerId(),result.getAnswerCount(),result.getQuestionId(),result.getTitle(),result.getOwner());
                          else
                        return new ViewModel(result.getAcceptedAnswerId(),result.getAnswerCount(),result.getQuestionId(),result.getTitle(),result.getOwner(),s.getOwner());
                    }
                }
        );
    }
}



