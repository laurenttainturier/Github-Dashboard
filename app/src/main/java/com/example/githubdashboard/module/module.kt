package com.example.githubdashboard.module

import android.app.Application
import androidx.room.Room
import com.example.githubdashboard.dao.GithubRepoDao
import com.example.githubdashboard.dao.UserDao
import com.example.githubdashboard.database.GithubDatabase
import com.example.githubdashboard.repository.GithubRepoRepository
import com.example.githubdashboard.repository.UserRepository
import com.example.githubdashboard.viewModel.GithubReposViewModel
import com.example.githubdashboard.viewModel.UserViewModel
import com.example.githubdashboard.webservices.Webservice
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
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

val webserviceModule = module {
    fun provideUserApi(retrofit: Retrofit): Webservice {
        return retrofit.create(Webservice::class.java)
    }

    single { provideUserApi(get()) }
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
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

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

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}