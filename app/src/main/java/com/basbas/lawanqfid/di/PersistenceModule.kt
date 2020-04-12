package com.basbas.lawanqfid.di

import com.basbas.lawanqfid.data.source.generated.AppGeneratedSource
import com.basbas.lawanqfid.data.source.pref.AppPrefSource
import org.koin.dsl.module

/**
 * rizmaulana@live.com 2019-06-14.
 */

val persistenceModule = module {
    single {
        AppPrefSource()
    }

    single {
        AppGeneratedSource()
    }
}


