package com.example.githubdashboard.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.githubdashboard.R
import com.example.githubdashboard.viewModel.GithubReposViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repository_detail.*
import kotlinx.android.synthetic.main.repository_detail.authorAvatar_TextView
import org.koin.android.viewmodel.ext.android.viewModel

class RepoDetailActivity: AppCompatActivity() {

    private val reposViewModel by viewModel<GithubReposViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repository_detail)
        val position = intent.getStringExtra(MainActivity.GITHUB_REPO_POSITION)?.toInt() ?: 0
        reposViewModel.githubRepos.observe(this, Observer { githubRepos ->
            val githubRepo = githubRepos[position]

            authorUsername_TextView.text = githubRepo.owner.username
            repoUrl_TextView.text = githubRepo.html_url
            repoName_TextView.text = githubRepo.name
            repoLanguage_TextView.text = githubRepo.language
            repoDescription_TextView.text = githubRepo.description

            Picasso.get().load(githubRepo.owner.avatarUrl).into(authorAvatar_TextView)
        })
    }
}
