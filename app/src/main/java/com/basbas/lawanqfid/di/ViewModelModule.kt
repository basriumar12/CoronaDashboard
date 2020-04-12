package com.basbas.lawanqfid.di

import com.basbas.lawanqfid.ui.dailygraph.DailyGraphViewModel
import com.basbas.lawanqfid.ui.detail.DetailViewModel
import com.basbas.lawanqfid.ui.overview.DashboardViewModel
import com.basbas.lawanqfid.ui.percountry.indonesia.CountryIndonesiaViewModel
import com.basbas.lawanqfid.ui.widget.LocationWidgetViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * rizmaulana 2020-02-24.
 */

val viewModelModule = module {
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { DailyGraphViewModel(get(), get()) }
    viewModel { CountryIndonesiaViewModel(get(), get()) }
    viewModel { LocationWidgetViewModel(get(), get()) }
}