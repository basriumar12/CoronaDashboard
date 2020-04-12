package com.basbas.lawanqfid.ui.widget

import com.basbas.lawanqfid.data.repository.Repository
import com.basbas.lawanqfid.ui.base.BaseViewModel
import com.basbas.lawanqfid.util.rx.SchedulerProvider

/**
 * rizmaulana 22/03/20.
 */
data class LocationWidgetViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

}