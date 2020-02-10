package com.example.githubdashboard.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GithubRepo (
    val name: String,
    @PrimaryKey val html_url: String,
    val description: String?,
    val language: String?,
    @Embedded val owner: User,
    @SerializedName("stargazers_count") val starNumber: Int
)
