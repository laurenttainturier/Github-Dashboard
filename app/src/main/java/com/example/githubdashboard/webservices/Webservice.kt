package com.example.githubdashboard.webservices

import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.model.User
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


interface Webservice {

    @GET("/users/{name}/repos")
    fun getRepos(@Path("name") username: String) : Call<List<GithubRepo>>

    @GET("/users/{name}")
    fun getUser(@Path("name") username: String) : Call<User>
}