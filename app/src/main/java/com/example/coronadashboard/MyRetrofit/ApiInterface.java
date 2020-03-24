package com.example.coronadashboard.MyRetrofit;


import com.example.coronadashboard.Models.ResponseCovid19;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("indonesia")
    Call<List<ResponseCovid19>> get_covid19_indonesia();

}
