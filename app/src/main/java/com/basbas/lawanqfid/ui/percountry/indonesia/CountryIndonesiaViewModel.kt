package com.basbas.lawanqfid.ui.percountry.indonesia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.data.mapper.IndonesiaDailyDataMapper
import com.basbas.lawanqfid.data.model.indonesia.IndonesiaDaily
import com.basbas.lawanqfid.data.model.indonesia.IndonesiaPerProvince
import com.basbas.lawanqfid.data.repository.Repository
import com.basbas.lawanqfid.ui.adapter.viewholders.TextItem
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.ui.base.BaseViewModel
import com.basbas.lawanqfid.util.Constant
import com.basbas.lawanqfid.util.SingleLiveEvent
import com.basbas.lawanqfid.util.ext.addTo
import com.basbas.lawanqfid.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * rizmaulana 22/03/20.
 */
class CountryIndonesiaViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _liveItems = MutableLiveData<List<BaseViewItem>>()
    val items: LiveData<List<BaseViewItem>>
        get() = _liveItems

    fun loadData() {
        Observable.zip(
            appRepository.getIndonesiaDaily(),
            appRepository.getIndonesiaPerProvince(),
            BiFunction<List<IndonesiaDaily>, List<IndonesiaPerProvince>, List<BaseViewItem>> { daily, province ->
                val list = mutableListOf<BaseViewItem>()
                if (province.isNullOrEmpty().not()) {
                    list.add(TextItem(R.string.case_per_province_chart))
                    list.add(IndonesiaDailyDataMapper.transformIntoCountryProvinceGraph(province))
                }
                if (daily.isNullOrEmpty().not()) {
//                    list.add(TextItem(R.string.case_daily_chart))
//                    list.add(IndonesiaDailyDataMapper.transformIntoCountryDailyGraph(daily))
                    list.add(TextItem(R.string.case_daily))
                    list.addAll(IndonesiaDailyDataMapper.transformToPerCountryDaily(daily.reversed()))
                }
                return@BiFunction list
            })
            .doOnSubscribe { _loading.postValue(true) }
            .doFinally {
                _loading.postValue(false)
            }
            .subscribe({
                _liveItems.postValue(it)
            }, {
                it.printStackTrace()
                _toastMessage.postValue(Constant.ERROR_MESSAGE)
            })
            .addTo(compositeDisposable)


    }

}