package com.asyncdroid.ads.di.module;


import com.asyncdroid.ads.util.APIInterface;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetworkModule {

    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    APIInterface providesAPIInterface(Retrofit retrofit) {
        return retrofit.create(APIInterface.class);
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient() {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        okHttpClientBuilder.addInterceptor(chain -> {

            final Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("Accept", "*/*");
            requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
            requestBuilder.method(original.method(), original.body());

            final Request request = requestBuilder.build();

            Response response = chain.proceed(request);

            final Buffer reqBuffer = new Buffer();
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                requestBody.writeTo(reqBuffer);
            }

            return response;
        });

        return okHttpClientBuilder.connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();
    }

}
