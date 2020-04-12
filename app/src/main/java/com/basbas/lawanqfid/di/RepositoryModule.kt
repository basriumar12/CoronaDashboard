package com.basbas.lawanqfid.di

import com.basbas.lawanqfid.data.repository.AppRepository
import com.basbas.lawanqfid.data.repository.Repository
import org.koin.dsl.module

/**
 * rizmaulana 2020-02-24.
 */
val repositoryModule = module {
    factory<Repository> {
        AppRepository(get(), get(), get())
    }
}