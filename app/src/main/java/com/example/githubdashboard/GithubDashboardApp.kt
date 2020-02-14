package com.example.githubdashboard

import android.app.Application
import com.example.githubdashboard.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class GithubDashboardApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GithubDashboardApp)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                webserviceModule,
                netModule,
                dbModule
            ))
        }
    }
}