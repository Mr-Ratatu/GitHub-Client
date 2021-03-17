package com.github.client

import android.app.Application
import com.github.client.di.networkModule
import com.github.client.di.repositoryModule
import com.github.client.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }

}