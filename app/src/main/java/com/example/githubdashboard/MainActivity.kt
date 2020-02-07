package com.example.githubdashboard

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.viewModel.GithubReposViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RepoAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        val reposViewModel by viewModel<GithubReposViewModel>()
        reposViewModel.githubRepos.observe(this, Observer<List<GithubRepo>> { githubRepos ->
            adapter.repos = githubRepos as MutableList<GithubRepo>
        })
    }

    fun searchUsername(view: View) {
        val username = username_editText.text.toString()
        username_textView.text = username
        username_layout.visibility = View.VISIBLE
        recyclerview.visibility = View.VISIBLE
    }
}
