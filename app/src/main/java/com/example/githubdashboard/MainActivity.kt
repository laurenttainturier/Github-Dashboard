package com.example.githubdashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.githubdashboard.extensions.hideKeyboard
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.viewModel.GithubReposViewModel
import com.example.githubdashboard.viewModel.UserViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    lateinit var adapter: RepoAdapter

    private val reposViewModel by viewModel<GithubReposViewModel>()

    private val userViewModel by viewModel<UserViewModel>()

    companion object {
        const val GITHUB_REPO_POSITION =
            "com.example.android.githubdashboard.extra.GITHUB_REPO_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reposViewModel.githubRepos.observe(this, Observer<List<GithubRepo>> { githubRepos ->
            adapter.updateRepos(githubRepos)
            adapter.notifyDataSetChanged()
        })

        userViewModel.user.observe(this, Observer { user ->
            username_textView.text = user?.username ?: ""
            if (username_textView.text.isBlank()) {
                username_layout.visibility = View.INVISIBLE
                no_user_found_textView.visibility = View.VISIBLE
            } else {
                username_layout.visibility = View.VISIBLE
                no_user_found_textView.visibility = View.INVISIBLE
            }

            Picasso.get().load(user?.avatarUrl).into(userAvatar_TextView)
        })

        swiperefresh.setOnRefreshListener {
            searchUsername()
            swiperefresh.isRefreshing = false
        }

        adapter = RepoAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    fun onSearchClick(view: View) {
        searchUsername()
        hideKeyboard(view)
    }

    private fun searchUsername() {
        val username = username_editText.text.toString()
        userViewModel.getUser(username)
        reposViewModel.getUserRepos(username)
    }
}
