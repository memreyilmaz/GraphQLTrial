package com.example.graphqltrial.ui.bio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqltrial.GetBioQuery
import com.example.graphqltrial.databinding.ItemRepositoryBinding
import com.example.graphqltrial.utils.DefaultDateTimeConverter
import com.example.graphqltrial.utils.showIfNotNull

class UserBioRepositoriesAdapter :
    RecyclerView.Adapter<UserBioRepositoriesAdapter.RepositoriesViewHolder>() {

    private var repositories: List<GetBioQuery.Node?>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val binding = LayoutInflater.from(parent.context).let {
            ItemRepositoryBinding.inflate(it, parent, false)
        }
        return RepositoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(repositories?.get(position))
    }

    override fun getItemCount() = repositories?.size ?: 0

    fun updateItems(newRepositories: List<GetBioQuery.Node?>?) {
        repositories = newRepositories
        notifyDataSetChanged()
    }

    class RepositoriesViewHolder(private val itemBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(repository: GetBioQuery.Node?) {
            repository?.let {
                itemBinding.apply {
                    texViewRepositoryName.text = it.name
                    texViewRepositoryDescription.showIfNotNull(it.description)
                    texViewRepositoryUrl.text = it.url.toString()
                    texViewRepositoryStarCount.text = it.stargazerCount.toString()
                    //texViewRepositoryCreationDate.text = it.createdAt.toString()

                    texViewRepositoryCreationDate.showIfNotNull(DefaultDateTimeConverter().formatDate(it.createdAt.toString()))
                }
            }
        }
    }
}