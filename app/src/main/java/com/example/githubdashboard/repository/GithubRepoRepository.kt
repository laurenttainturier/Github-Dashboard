package com.example.githubdashboard.repository

import com.example.githubdashboard.dao.GithubRepoDao
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.webservices.Webservice
import retrofit2.Call
import retrofit2.Response


class GithubRepoRepository (private val webservice: Webservice, val repoDao: GithubRepoDao){

    fun getGithubRepos(username: String, block: (List<GithubRepo>) -> Unit) {

        val userRepos = repoDao.getUserRepos(username)
        if (userRepos.isNotEmpty()) {
            block(userRepos)
        } else {
            webservice.getRepos(username).enqueue(object : retrofit2.Callback<List<GithubRepo>> {
                override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                    block(emptyList())
                }

                override fun onResponse(
                    call: Call<List<GithubRepo>>,
                    response: Response<List<GithubRepo>>
                ) {
                    block(response.body() ?: emptyList())
                    if (response.body() != null)
                        repoDao.insertRepos(response.body()!!)
                }
            })
        }

    }
}
