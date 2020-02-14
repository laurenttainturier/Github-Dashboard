package com.example.githubdashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

        username_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                userViewModel.getAllUsers(username_editText.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        reposViewModel.githubRepos.observe(this, Observer<List<GithubRepo>> { githubRepos ->
            adapter.updateRepos(githubRepos)
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

            // load user profile picture using Picasso library
            Picasso.get().load(user?.avatarUrl).into(userAvatar_TextView)
        })

        userViewModel.possibleUsers.observe(this, Observer { users ->
            val usernames = users.map { user -> user.username }
            val adapter = ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, usernames
            )
            adapter.notifyDataSetChanged()
            username_editText.setAdapter(adapter)
        })

        // refresh repository when user swipes
        swiperefresh.setOnRefreshListener {
            reposViewModel.getUserRepos(username_textView.text.toString())
            swiperefresh.isRefreshing = false
        }

        // attach the adapter to the recycler view
        adapter = RepoAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    fun onSearchClick(view: View) {
        val username = username_editText.text.toString()
        userViewModel.getUser(username)
        reposViewModel.getUserRepos(username)
        hideKeyboard(view)
    }
}
