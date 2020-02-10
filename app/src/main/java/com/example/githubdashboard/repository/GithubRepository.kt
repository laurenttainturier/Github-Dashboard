package com.example.githubdashboard.repository

import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.webservices.Webservice
import retrofit2.Call
import retrofit2.Response


class GithubRepository (private val webservice: Webservice){

    fun getGithubRepos(username: String, block: (List<GithubRepo>) -> Unit) {

        webservice.getRepos(username).enqueue(object : retrofit2.Callback<List<GithubRepo>> {
            override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<List<GithubRepo>>,
                response: Response<List<GithubRepo>>
            ) {
                block(response.body() ?: emptyList())
            }
        })
    }
}
