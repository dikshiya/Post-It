package com.example.postit.network;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://dummyapi.io/data/api/";
    private static Retrofit mRetrofit;
    private static ApiClient sInstance;

    private ApiClient() {
    }

    public static synchronized ApiClient getInstance() {
        if (sInstance == null) {
            sInstance = new ApiClient();
            createRetrofit();
        }
        return sInstance;
    }

    private static void createRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    @NonNull
    private static okhttp3.OkHttpClient getOkHttpClient() {
        //create a builder
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //create HttpLogging interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        //add interceptor to builder
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    @NonNull
    public <S> S createService(Class<S> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

}
