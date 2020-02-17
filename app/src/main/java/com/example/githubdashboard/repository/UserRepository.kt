package com.example.githubdashboard.repository

import com.example.githubdashboard.dao.UserDao
import com.example.githubdashboard.webservices.NoConnectivityException
import com.example.githubdashboard.model.User
import com.example.githubdashboard.webservices.Webservice
import retrofit2.Call
import retrofit2.Response


class UserRepository(private val webservice: Webservice, val userDao: UserDao) {

    fun getUser(username: String, block: (User?) -> Unit) {

        webservice.getUser(username).enqueue(object : retrofit2.Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                if (t is NoConnectivityException) {
                    val user = userDao.getUser(username)
                    block(user)
                } else block(null)
            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                block(response.body())
                if (response.body() != null)
                    userDao.insertUser(response.body()!!)
            }
        })
    }

    fun getAllUsers(username: String, block: (List<User>) -> Unit) {
        block(userDao.getSpecificUsers(username))
    }
}

