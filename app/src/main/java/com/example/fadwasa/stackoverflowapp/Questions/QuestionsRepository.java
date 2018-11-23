package com.example.fadwasa.stackoverflowapp.Questions;








        import android.util.Log;


        import com.example.fadwasa.stackoverflowapp.http.QuestionInfo;
        import com.example.fadwasa.stackoverflowapp.http.UserAnsweredInfo;
        import com.example.fadwasa.stackoverflowapp.http.apimodel.AItem;
        import com.example.fadwasa.stackoverflowapp.http.apimodel.AOutput;
        import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;
        import com.example.fadwasa.stackoverflowapp.http.apimodel.QItem;
        import com.example.fadwasa.stackoverflowapp.http.apimodel.QOutput;

        import java.util.ArrayList;
        import java.util.List;

        import io.reactivex.Observable;
        import io.reactivex.functions.BiFunction;
        import io.reactivex.functions.Consumer;
        import io.reactivex.functions.Function;


public class QuestionsRepository implements Repository {

    private QuestionInfo questionInfo;
    private UserAnsweredInfo userInfo;
    //private MoreInfoApiService moreInfoApiService;
    //private List<String> countries;
    private List<QItem> results;
    private List<AItem> results1;

    private long timestamp;
    private List<AOwner> owners;
    private boolean questionsSame = false;


    private String questionID;

    private static final long STALE_MS = 20 * 1000; // Data is stale after 20 seconds

    public QuestionsRepository(QuestionInfo questionInfo, UserAnsweredInfo userInfo) {
        //this.moreInfoApiService = moreInfoApiService;
        this.timestamp = System.currentTimeMillis();
        this.questionInfo = questionInfo;
        this.userInfo=userInfo;
        owners = new ArrayList<>();
        results = new ArrayList<>();
        results1 = new ArrayList<>();

    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

    @Override
    public Observable<QItem> getResultsFromMemory(String userID) {
        if (isUpToDate()&&results.size() != 0) {
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
    public Observable<AOwner> getAcceptedFromMemory() {
        if (isUpToDate()) {
            if(questionsSame)
                return Observable.fromIterable(owners);
            else {
                timestamp = System.currentTimeMillis();
                owners.clear();
                return Observable.empty();
            }
        } else {
            timestamp = System.currentTimeMillis();
            owners.clear();
            return Observable.empty();
        }
    }


    @Override
    public Observable<AOwner> getAcceptedFromNetwork(String userID) {
        return getResultsFromNetwork1(userID)
                .concatMap(new Function<AItem, Observable<AOwner>>() {
                    @Override
                    public Observable<AOwner> apply(AItem result) {
                        return Observable.just(result.getOwner());
                    }
                }) .doOnNext(new Consumer<AOwner>() {
                    @Override
                    public void accept(AOwner s) {

                        owners.add(s);
                    }
                });
    }

    public Observable<AItem> getResultsFromNetwork1(String userID) {


        return getResultsFromNetwork(userID).concatMap(new Function<QItem, Observable<AOutput>>() {

            @Override
            public Observable<AOutput> apply(QItem output) {

                Log.d("output","is88"+output.getQuestionId());
                Log.d("userInfo","is0000"+userInfo);

                return userInfo.getAnsweredInfo(output.getQuestionId()+"","desc","activity");
            }
        }). concatMap(new Function<AOutput, Observable<AItem>>() {


            @Override
            public Observable<AItem> apply(AOutput output) throws Exception {
                return Observable.fromIterable(output.getItems());
            }
        }).doOnNext(new Consumer<AItem>() {
            @Override
            public void accept(AItem result) {

                if(result.getIsAccepted())
                    results1.add(result);
            }
        });
    }







    @Override
    public Observable<QItem> getResultData(String userID) {
        return getResultsFromMemory(userID).switchIfEmpty(getResultsFromNetwork( userID));
    }
    @Override
    public Observable<AOwner> getAcceptedData(String accountID) {
        return getAcceptedFromMemory().switchIfEmpty(getAcceptedFromNetwork(accountID));
    }
    @Override
    public Observable<ViewModel> getFinalResult(String accountID) {
        return Observable.zip(
                getResultData(accountID),
                getAcceptedData(accountID),

                new BiFunction<QItem, AOwner, ViewModel>() {
                    @Override
                    public ViewModel apply(QItem result, AOwner s) {
                        return new ViewModel(result.getAcceptedAnswerId(),result.getAnswerCount(),result.getQuestionId(),result.getTitle(),result.getOwner(),s);
                    }
                }
        );
    }
}


