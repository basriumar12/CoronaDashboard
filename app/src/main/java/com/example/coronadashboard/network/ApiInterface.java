package com.example.coronadashboard.network;


import com.example.coronadashboard.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    //endpoint
    @GET("indonesia")
    Call<List<ResponseData>> getData();

}
