package com.example.githubdashboard.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubdashboard.model.User

@Dao
interface UserDao {
    @Query("select * from user where username=:username LIMIT 1")
    fun getUser(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("delete from user")
    fun deleteAllUsers()
}