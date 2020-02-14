package com.example.githubdashboard.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.model.User
import com.example.githubdashboard.repository.UserRepository


class UserViewModel(private val repository: UserRepository) : ViewModel() {

    var user: MutableLiveData<User?> = MutableLiveData()

    var possibleUsers : MutableLiveData<List<User>> = MutableLiveData()

    fun getUser(username: String) {
        repository.getUser(username) { user ->
            this.user.value = user
        }
    }

    fun getAllUsers(username: String) {
        return repository.getAllUsers(username) { users ->
            possibleUsers.value = users
        }
    }
}
