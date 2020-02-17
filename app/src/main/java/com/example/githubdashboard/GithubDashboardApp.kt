package com.example.githubdashboard

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.githubdashboard.dao.GithubRepoDao
import com.example.githubdashboard.dao.UserDao
import com.example.githubdashboard.database.GithubDatabase
import com.example.githubdashboard.extensions.NetworkConnectionInterceptor
import com.example.githubdashboard.repository.GithubRepoRepository
import com.example.githubdashboard.repository.UserRepository
import com.example.githubdashboard.viewModel.GithubReposViewModel
import com.example.githubdashboard.viewModel.UserViewModel
import com.example.githubdashboard.webservices.webserviceModule
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val repositoryModule = module {
    factory { GithubRepoRepository(get(), get()) }
    factory { UserRepository(get(), get()) }
}

val viewModelModule = module {
    single { GithubReposViewModel(get()) }
    single { UserViewModel(get()) }
}

val dbModule = module {

    fun provideDatabase(application: Application): GithubDatabase {
        return Room.databaseBuilder(application, GithubDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideRepoDao(githubDatabase: GithubDatabase): GithubRepoDao {
        return githubDatabase.githubRepoDao
    }

    fun provideUserDao(githubDatabase: GithubDatabase) : UserDao {
        return githubDatabase.userDao
    }

    single { provideDatabase(androidApplication())}
    single { provideRepoDao(get()) }
    single { provideUserDao(get()) }
}

val netModule = module {
    /*fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }*/

    fun provideHttpClient(context: Context): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context))
//            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

//    single { provideCache(androidApplication()) }
    single { provideHttpClient(androidContext()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}


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