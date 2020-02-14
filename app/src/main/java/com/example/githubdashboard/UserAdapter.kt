package com.example.githubdashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubdashboard.model.GithubRepo
import kotlinx.android.synthetic.main.repository_item.view.*


class UserAdapter(context: Context) : RecyclerView.Adapter<UserAdapter.RepoViewHolder>() {

    var users: List<GithubRepo> = listOf<GithubRepo>().toMutableList()
        set(value) {
            field = value
        }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = inflater.inflate(
            R.layout.repository_item, parent, false
        )

        return RepoViewHolder(view, this)
    }

    fun updateRepos(newRepos: List<GithubRepo>) {
        users = newRepos
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindRepos(users[position])
    }

    class RepoViewHolder(view: View, private val adapter: UserAdapter) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        fun bindRepos(repo: GithubRepo) = with(repo) {
            itemView.repoName.text = repo.name
        }

        override fun onClick(v: View) {
            val repoDetailIntent = Intent(v.context, RepoDetailActivity::class.java)
            repoDetailIntent.putExtra(MainActivity.GITHUB_REPO_POSITION, layoutPosition.toString())
            v.context.startActivity(repoDetailIntent)
        }
    }
}