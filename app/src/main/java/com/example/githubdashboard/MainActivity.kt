package com.example.githubdashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.viewModel.GithubReposViewModel
import com.example.githubdashboard.viewModel.UserViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.app.Activity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.inputmethod.InputMethodManager


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

        adapter = RepoAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    fun searchUsername(view: View) {
        val username = username_editText.text.toString()
        userViewModel.getUser(username)
        reposViewModel.getUserRepos(username)
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
