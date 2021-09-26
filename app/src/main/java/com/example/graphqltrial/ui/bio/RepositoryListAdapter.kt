package com.example.graphqltrial.ui.bio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqltrial.databinding.ItemRepositoryBinding
import com.example.graphqltrial.utils.DefaultDateTimeConverter
import com.example.graphqltrial.data.model.Repository
import com.example.graphqltrial.utils.showIfNotNull

class RepositoryListAdapter : RecyclerView.Adapter<RepositoryListAdapter.RepositoriesViewHolder>() {

    private var repositories: List<Repository?> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val binding = LayoutInflater.from(parent.context).let {
            ItemRepositoryBinding.inflate(it, parent, false)
        }
        return RepositoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount() = repositories.size

    fun updateItems(newRepositories: List<Repository>) {
        repositories = newRepositories
        notifyDataSetChanged()
    }

    class RepositoriesViewHolder(private val itemBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(repository: Repository?) {
            repository?.let {
                itemBinding.apply {
                    texViewRepositoryName.text = it.name
                    texViewRepositoryDescription.showIfNotNull(it.description)
                    texViewRepositoryUrl.text = it.url
                    texViewRepositoryStarCount.text = it.stargazerCount
                    texViewRepositoryCreationDate.showIfNotNull(
                        DefaultDateTimeConverter().formatDate(
                            it.creationDate
                        )
                    )
                }
            }
        }
    }
}