package com.basbas.lawanqfid.data.source.remote

import com.basbas.lawanqfid.data.model.BaseResponse
import com.basbas.lawanqfid.data.model.CovidDaily
import com.basbas.lawanqfid.data.model.CovidDetail
import com.basbas.lawanqfid.data.model.CovidOverview
import com.basbas.lawanqfid.data.model.indonesia.IndonesiaDaily
import com.basbas.lawanqfid.data.model.indonesia.IndonesiaPerProvince
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

@JvmSuppressWildcards
interface Api {
    @GET("api")
    fun overview(): Observable<CovidOverview>

    @GET("api/daily")
    fun daily(): Observable<List<CovidDaily>>

    @GET("api/confirmed")
    fun confirmed(): Observable<List<CovidDetail>>

    @GET("api/deaths")
    fun deaths(): Observable<List<CovidDetail>>

    @GET("api/recovered")
    fun recovered(): Observable<List<CovidDetail>>

    @GET("api/countries/{country}")
    fun country(@Path("country") country: String): Observable<CovidOverview>

    @GET
    fun getIndonesiaDaily(@Url url: String = "https://indonesia-covid-19.mathdro.id/api/harian"): Observable<BaseResponse<List<IndonesiaDaily>>>

    @GET
    fun getIndonesiaPerProvince(@Url url: String = "https://indonesia-covid-19.mathdro.id/api/provinsi"): Observable<BaseResponse<List<IndonesiaPerProvince>>>


}