package com.example.githubdashboard.webservices

import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.model.User
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path


val webserviceModule = module {
    fun provideUserApi(retrofit: Retrofit): Webservice {
        return retrofit.create(Webservice::class.java)
    }

    single { provideUserApi(get()) }
}


interface Webservice {

    @GET("/users/{name}/repos")
    fun getRepos(@Path("name") username: String) : Call<List<GithubRepo>>

    @GET("/users/{name}")
    fun getUser(@Path("name") username: String) : Call<User>

    @Headers("Authorization: token 1c0bdca1bd0a61091b089716395d3a4ee1706d99")
    @GET("/users?per_page=100")
    fun getUsersPage() : Call<List<User>>
}