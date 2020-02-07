package com.example.githubdashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubdashboard.model.GithubRepo
import kotlinx.android.synthetic.main.repository_item.view.*


class RepoAdapter(context: Context) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    var repos: MutableList<GithubRepo> = listOf<GithubRepo>().toMutableList()
    set(value) {
        field = value
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = inflater.inflate(
            R.layout.repository_item, parent, false
        )

        return RepoViewHolder(view)
    }

    fun updateRepos(newRepos: MutableList<GithubRepo>) {
        repos = newRepos
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindRepos(repos[position])
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindRepos(repo: GithubRepo) = with(repo) {
            itemView.repoName.text = repo.name
        }
    }
}