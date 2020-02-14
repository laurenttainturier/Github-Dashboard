package com.example.githubdashboard.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.model.User

@Dao
interface GithubRepoDao {
    @Query("select * from GithubRepo where username = lower(:username)")
    fun getUserRepos(username: String): List<GithubRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<GithubRepo>)

    @Query("delete from GithubRepo")
    fun deleteAllRepos()
}