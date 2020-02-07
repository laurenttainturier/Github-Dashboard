package com.example.githubdashboard.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.repository.GithubRepository
import org.koin.dsl.module


val viewModelModule = module {
    single {GithubReposViewModel(get())}
}

class GithubReposViewModel(repository: GithubRepository) : ViewModel() {

    var githubRepos : MutableLiveData<List<GithubRepo>> = repository.getGithubRepos("laurenttainturier") as MutableLiveData<List<GithubRepo>>
    
}
