package com.example.githubdashboard

import android.content.Context
import androidx.room.Room
import com.example.githubdashboard.dao.GithubRepoDao
import com.example.githubdashboard.dao.UserDao
import com.example.githubdashboard.database.GithubDatabase
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.koin.test.KoinTest


//@RunWith(AndroidJUnit4::class)
class ExampleUnitTest: KoinTest{

    private lateinit var userDao: UserDao
    private lateinit var db: GithubDatabase


    @Before
    fun createDb() {

//        val context = ApplicationProvider.getApplicationContext()
//        db = Room.inMemoryDatabaseBuilder(
//            context, GithubDatabase::class.java).build()
//        userDao = db.userDao
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


}
