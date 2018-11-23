package com.example.fadwasa.stackoverflowapp.http;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModuleForInfo {

    public final String BASE_URL = "https://api.stackexchange.com/2.2/";



    @Provides
    public OkHttpClient provideClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(
                        "site",
                        "stackoverflow"
                ).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public UserInfo provideApiService() {
        return provideRetrofit(BASE_URL, provideClient()).create(UserInfo.class);
    }
    @Provides
    public QuestionInfo provideApiServiceQ() {
        return provideRetrofit(BASE_URL, provideClient()).create(QuestionInfo.class);
    }

    @Provides
    public UserAnsweredInfo provideApiServiceA() {
        return provideRetrofit(BASE_URL, provideClient()).create(UserAnsweredInfo.class);
    }

}
