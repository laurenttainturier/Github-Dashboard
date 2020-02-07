package com.example.githubdashboard.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.webservices.Webservice
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Response


val repositoryModule = module {
    factory { GithubRepository(get())}
}


class GithubRepository (private val webservice: Webservice){

    suspend fun getUserRepos(username: String) = webservice.getUserRepos(username)

    fun getGithubRepos(username: String) : LiveData<List<GithubRepo>> {
        val data = MutableLiveData<List<GithubRepo>>()
        webservice.getRepos(username).enqueue(object: retrofit2.Callback<List<GithubRepo>>{
            override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                TODO("not implemented")
            }

            override fun onResponse(call : Call<List<GithubRepo>>, response: Response<List<GithubRepo>>) {
                data.value = response.body()
            }
        })

        return data
    }
}
