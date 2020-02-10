package com.example.githubdashboard.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @SerializedName("login") @PrimaryKey val username: String,
    @SerializedName("avatar_url") val avatarUrl: String
)