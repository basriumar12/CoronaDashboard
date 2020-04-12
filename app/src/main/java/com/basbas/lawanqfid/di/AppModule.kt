package com.basbas.lawanqfid.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.util.rx.AppSchedulerProvider
import com.basbas.lawanqfid.util.rx.SchedulerProvider

import org.koin.dsl.module
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * rizmaulana 2020-02-24.
 */
const val DEFAULT_FONT = "fonts/GoogleSans-Regular.ttf"

val appModule = module {

    single {
        CalligraphyConfig.Builder()
            .setDefaultFontPath(DEFAULT_FONT)
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

    factory<SchedulerProvider> {
        AppSchedulerProvider()
    }


    factory {
        LinearLayoutManager(get())
    }

}