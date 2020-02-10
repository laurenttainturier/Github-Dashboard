package com.example.githubdashboard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubdashboard.dao.GithubRepoDao
import com.example.githubdashboard.dao.UserDao
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.model.User


@Database(
    entities = [
        User::class,
        GithubRepo::class
    ],
    version = 3,
    exportSchema = false
)

abstract class GithubDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val githubRepoDao: GithubRepoDao
}
