package com.basbas.lawanqfid.utama.network;

/**
 * Created by macbookair on 5/19/17.
 */

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFromSpreadsheet {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    //berita
//https://script.google.com/macros/s/AKfycbzr4cPpmQ8nwTQp0pUiEbIq40XPMANcGdCOY4Rsxe_6Wc0Y2fA/exec
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
