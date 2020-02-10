package com.example.githubdashboard.model

import com.google.gson.annotations.SerializedName

data class GithubRepo (
    val username: String,
    val name: String,
    val html_url: String,
    val description: String?,
    val language: String,
    val owner: User,
    @SerializedName("stargazers_count")
    val starNumber: Int
)
