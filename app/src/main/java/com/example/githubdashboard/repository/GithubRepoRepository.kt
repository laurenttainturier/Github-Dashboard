package com.example.githubdashboard.repository

import com.example.githubdashboard.dao.GithubRepoDao
import com.example.githubdashboard.extensions.NoConnectivityException
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.webservices.Webservice
import retrofit2.Call
import retrofit2.Response


class GithubRepoRepository(private val webservice: Webservice, val repoDao: GithubRepoDao) {

    fun getGithubRepos(username: String, block: (List<GithubRepo>) -> Unit) {

        webservice.getRepos(username).enqueue(object : retrofit2.Callback<List<GithubRepo>> {
            override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                if (t is NoConnectivityException)
                    block(repoDao.getUserRepos(username))
                else block(emptyList())
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
