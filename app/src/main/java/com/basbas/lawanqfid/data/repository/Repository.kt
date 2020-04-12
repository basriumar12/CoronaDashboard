package com.basbas.lawanqfid.data.repository

import com.basbas.lawanqfid.data.model.CovidDaily
import com.basbas.lawanqfid.data.model.CovidDetail
import com.basbas.lawanqfid.data.model.CovidOverview
import com.basbas.lawanqfid.data.model.indonesia.IndonesiaDaily
import com.basbas.lawanqfid.data.model.indonesia.IndonesiaPerProvince
import com.basbas.lawanqfid.ui.adapter.viewholders.PerCountryItem
import io.reactivex.Completable
import io.reactivex.Observable

interface Repository {
    fun overview(): Observable<Result<CovidOverview>>
    fun daily(): Observable<Result<List<CovidDaily>>>
    fun confirmed(): Observable<List<CovidDetail>>
    fun deaths(): Observable<List<CovidDetail>>
    fun recovered(): Observable<List<CovidDetail>>
    fun country(id: String): Observable<CovidOverview>
    fun fullStats(): Observable<List<CovidDetail>>
    fun pinnedRegion(): Observable<Result<CovidDetail>>
    fun putPinnedRegion(data: CovidDetail): Completable
    fun removePinnedRegion(): Completable
    fun getCachePinnedRegion(): CovidDetail?
    fun getCacheOverview(): CovidOverview?
    fun getCacheDaily(): List<CovidDaily>?
    fun getCacheConfirmed(): List<CovidDetail>?
    fun getCacheDeath(): List<CovidDetail>?
    fun getCacheRecovered(): List<CovidDetail>?
    fun getCacheFull(): List<CovidDetail>?
    fun getCacheCountry(id: String): CovidOverview?
    fun getPerCountryItem(): List<PerCountryItem>
    fun getIndonesiaDaily(): Observable<List<IndonesiaDaily>>
    fun getIndonesiaPerProvince(): Observable<List<IndonesiaPerProvince>>

}