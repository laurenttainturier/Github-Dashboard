package com.example.githubdashboard.webservices

import com.example.githubdashboard.model.GithubRepo
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


val webserviceModule = module {
    fun provideUserApi(retrofit: Retrofit): Webservice {
        return retrofit.create(Webservice::class.java)
    }

    single { provideUserApi(get()) }
}


interface Webservice {
    @GET("/users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String) : List<GithubRepo>

    @GET("/users/{username}/repos")
    fun getRepos(@Path("username") username: String) : Call<List<GithubRepo>>
}