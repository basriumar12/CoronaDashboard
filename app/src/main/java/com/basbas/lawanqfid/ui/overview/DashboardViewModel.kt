package com.basbas.lawanqfid.ui.overview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.data.mapper.CovidDailyDataMapper
import com.basbas.lawanqfid.data.mapper.CovidOverviewDataMapper
import com.basbas.lawanqfid.data.mapper.CovidPinnedDataMapper
import com.basbas.lawanqfid.data.model.CovidDaily
import com.basbas.lawanqfid.data.model.CovidDetail
import com.basbas.lawanqfid.data.model.CovidOverview
import com.basbas.lawanqfid.data.repository.Repository
import com.basbas.lawanqfid.data.repository.Result
import com.basbas.lawanqfid.ui.adapter.viewholders.ErrorStateItem
import com.basbas.lawanqfid.ui.adapter.viewholders.LoadingStateItem
import com.basbas.lawanqfid.ui.adapter.viewholders.TextItem
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.ui.base.BaseViewModel
import com.basbas.lawanqfid.util.Constant
import com.basbas.lawanqfid.util.SingleLiveEvent
import com.basbas.lawanqfid.util.ext.addTo
import com.basbas.lawanqfid.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.functions.Function3

/**
 * rizmaulana
 */
class DashboardViewModel(
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

    private fun showLoadingState(){
        if(_liveItems.value?.isEmpty() == null ||
            _liveItems.value?.firstOrNull { it is ErrorStateItem } != null){
            _liveItems.value = listOf(LoadingStateItem())
        }
    }

    private fun showErrorState(throwable: Throwable){
        _loading.value = false
        if(_liveItems.value?.isEmpty() == null ||
            _liveItems.value?.firstOrNull { it is ErrorStateItem || it is LoadingStateItem } != null){
            _liveItems.value = listOf(handleThrowable(throwable))
        }
    }

    fun loadDashboard() {
        showLoadingState()

        val overviewObservable = appRepository.overview()
            .observeOn(schedulerProvider.io()) //all stream below will be manage on io thread

        val dailyObservable = appRepository.daily()
            .observeOn(schedulerProvider.io())

        val pinnedObservable = appRepository.pinnedRegion()
            .observeOn(schedulerProvider.io())

        Observable.combineLatest(
            overviewObservable,
            dailyObservable,
            pinnedObservable,
            Function3<Result<CovidOverview>,
                    Result<List<CovidDaily>>,
                    Result<CovidDetail>,
                    Pair<List<BaseViewItem>, Throwable?>> { overview, daily, pinned ->

                val items: MutableList<BaseViewItem> = mutableListOf()
                var currentThrowable: Throwable? = null

                with(overview){
                    items.add(CovidOverviewDataMapper.transform(data))
                    error?.let { currentThrowable = it }
                }

                with(pinned){
                    CovidPinnedDataMapper.transform(data)?.let {
                        items.add(it)
                    }
                    error?.let { currentThrowable = it }
                }

                //items.add(TextItem(R.string.per_country))
                items.addAll(appRepository.getPerCountryItem())

                with(daily){
                    val dailies = CovidDailyDataMapper.transform(data)
                    if(dailies.isNotEmpty()) {
                        items.add(TextItem(R.string.daily_updates, R.string.show_graph))
                        items.addAll(dailies)
                    }
                    error?.let { currentThrowable = it }
                }

                return@Function3 items.toList() to currentThrowable
            })
            .observeOn(schedulerProvider.ui()) //go back to ui thread
            .subscribe({ (result, throwable) ->
                _liveItems.postValue(result)

                //For now only check if there is a throwable
                if(throwable != null) _toastMessage.value = Constant.ERROR_MESSAGE
                _loading.value = false
            }, {
                _toastMessage.value = Constant.ERROR_MESSAGE
                showErrorState(it)
            }).addTo(compositeDisposable)
    }
}