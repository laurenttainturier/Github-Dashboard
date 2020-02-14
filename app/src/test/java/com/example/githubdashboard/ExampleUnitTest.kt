package com.example.githubdashboard

import android.app.Application
import androidx.room.Room
import com.example.githubdashboard.dao.GithubRepoDao
import com.example.githubdashboard.dao.UserDao
import com.example.githubdashboard.database.GithubDatabase
import com.example.githubdashboard.model.User
import com.example.githubdashboard.module.netModule
import com.example.githubdashboard.module.repositoryModule
import com.example.githubdashboard.module.viewModelModule
import com.example.githubdashboard.module.webserviceModule
import com.example.githubdashboard.viewModel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.mockito.Mockito.mock


val dbTestModule = module {

    fun provideDatabase(application: Application): GithubDatabase {
        return Room.inMemoryDatabaseBuilder(application, GithubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    fun provideRepoDao(githubDatabase: GithubDatabase): GithubRepoDao {
        return githubDatabase.githubRepoDao
    }

    fun provideUserDao(githubDatabase: GithubDatabase) : UserDao {
        return githubDatabase.userDao
    }

    single { provideDatabase(androidApplication())}
    single { provideRepoDao(get()) }
    single { provideUserDao(get()) }
}


class KoinTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KoinTestApp)
            modules(emptyList())
        }
    }

    internal fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}



class ExampleUnitTest : KoinTest {

    private val userDao: UserDao by inject()
    private val repoDao: GithubRepoDao by inject()
    private val userModel: UserViewModel by inject()

    @Before
    fun init() {
        startKoin {
            androidContext(KoinTestApp())
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    webserviceModule,
                    netModule,
                    dbTestModule
                )
            )
        }
    }

    /*@Test
    fun check() {
        assertEquals({}, userModel.user)
    }*/

    /*fun createDb() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    webserviceModule,
                    netModule,
                    dbModule
                )
            )
        }
    }*/


    @Test
    fun checkInsert() {
        val user = User("username", "avatarUrl")
        db.userDao.insertUser(user)
//        db.userDao.insertUser(user)
//        userDao.insertUser(user)
//        assertEquals("username", userDao.getUser("username"))
    }
}
