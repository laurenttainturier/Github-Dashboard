package com.example.githubdashboard.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.repository.GithubRepoRepository


class GithubReposViewModel(private val repoRepository: GithubRepoRepository) : ViewModel() {

    private var innerGithubRepos : MutableLiveData<List<GithubRepo>> = MutableLiveData()

    val githubRepos : LiveData<List<GithubRepo>>
    get() = innerGithubRepos

    fun getUserRepos(username: String) {
            repoRepository.getGithubRepos(username) {
            innerGithubRepos.value = it
        }
    }
}
