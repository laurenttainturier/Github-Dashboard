package com.example.githubdashboard

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubdashboard.model.GithubRepo
import com.example.githubdashboard.model.User
import com.example.githubdashboard.viewModel.GithubReposViewModel
import com.example.githubdashboard.viewModel.UserViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.URL


class MainActivity : AppCompatActivity() {

    lateinit var adapter: RepoAdapter

    private val reposViewModel by viewModel<GithubReposViewModel>()

    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reposViewModel.githubRepos.observe(this, Observer<List<GithubRepo>> { githubRepos ->
            adapter.updateRepos(githubRepos as MutableList<GithubRepo>)
            adapter.notifyDataSetChanged()
        })

        userViewModel.user.observe(this, Observer<User> { user ->
            username_textView.text = user.name
            Picasso.get().load(user.avatar_url).into(imageView)
        })

        adapter = RepoAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    fun searchUsername(view: View) {
        val username = username_editText.text.toString()
        userViewModel.getUser(username)
        reposViewModel.getUserRepos(username)
        username_layout.visibility = View.VISIBLE
    }
}
