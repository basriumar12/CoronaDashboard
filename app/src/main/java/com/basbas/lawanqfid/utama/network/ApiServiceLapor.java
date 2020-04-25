package com.basbas.lawanqfid.utama.network;

/**
 * Created by macbookair on 5/19/17.
 */

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceLapor {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    //https://script.google.com/macros/s/AKfycbzMDMK24u4h7YeiB0F3C_ieqnPwwAWcOkuOVlvNw3kKBUdgI1T1/
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("https://script.google.com/macros/s/AKfycbzMDMK24u4h7YeiB0F3C_ieqnPwwAWcOkuOVlvNw3kKBUdgI1T1/")
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static Retrofit retrofit = builder.build();
}
