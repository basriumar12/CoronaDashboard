package com.basbas.lawanqfid.network;

/**
 * Created by macbookair on 5/19/17.
 */

import com.basbas.lawanqfid.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFromSpreadsheet {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("https://script.google.com/macros/s/AKfycbzctxBEOf7kp94IBYM0GVQOmIYVgAiTScj_6iKh9ITGh8m2MRLp/")
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static Retrofit retrofit = builder.build();
}
