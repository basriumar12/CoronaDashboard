package com.example.coronadashboard.network;


import com.example.coronadashboard.model.ResponseData;
import com.example.coronadashboard.model.ResponseDataWorld;
import com.example.coronadashboard.model.provinsi.ResponseDataProvinsi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    //endpoint
    @GET("indonesia")
    Call<List<ResponseData>> getData();

    @GET("indonesia/provinsi")
    Call<List<ResponseDataProvinsi>> getDataProvinsi();

    @GET("/")
    Call<List<ResponseDataWorld>> getDataDunia();

}
