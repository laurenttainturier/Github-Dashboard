package com.example.githubdashboard.repository

import com.example.githubdashboard.model.User
import com.example.githubdashboard.webservices.Webservice
import retrofit2.Call
import retrofit2.Response


class UserRepository (private val webservice: Webservice){

    fun getUser(username: String, block : (User?) -> Unit) {
        webservice.getUser(username).enqueue(object: retrofit2.Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                block(response.body())
            }
        })
    }
}
