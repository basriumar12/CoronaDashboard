package com.basbas.lawanqfid.utama.network;


import com.basbas.lawanqfid.utama.model.ResponseData;
import com.basbas.lawanqfid.utama.model.ResponseDataWorld;
import com.basbas.lawanqfid.utama.model.berita.ResponseBerita;
import com.basbas.lawanqfid.utama.model.provinsi.ResponseDataProvinsi;
import com.basbas.lawanqfid.utama.model.youtube.ResponseYoutube;
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.model.ResponseDataFromSpreadSheet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //endpoint
    @GET("indonesia")
    Call<List<ResponseData>> getData();
//https://script.google.com/macros/s/AKfycbzctxBEOf7kp94IBYM0GVQOmIYVgAiTScj_6iKh9ITGh8m2MRLp/exec?
    @GET("exec")
    Call<ResponseDataFromSpreadSheet> getDataFromSpreadsheet(@Query("id")String id, @Query("sheet")String sheet);
    @GET("exec")
    Call<ResponseBerita> getDataBerita(@Query("id")String id, @Query("sheet")String sheet);

    @GET("exec")
    Call<ResponseYoutube> getDataYoutube(@Query("id")String id, @Query("sheet")String sheet);

    @GET("indonesia/provinsi")
    Call<List<ResponseDataProvinsi>> getDataProvinsi();

    @GET("/")
    Call<List<ResponseDataWorld>> getDataDunia();



}
