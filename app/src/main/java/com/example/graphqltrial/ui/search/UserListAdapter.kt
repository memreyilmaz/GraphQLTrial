package com.example.graphqltrial.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqltrial.data.model.User
import com.example.graphqltrial.databinding.ItemUserBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private var userList: List<User?> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = LayoutInflater.from(parent.context).let {
            ItemUserBinding.inflate(it, parent, false)
        }
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size

    fun updateItems(newUsers: List<User>) {
        userList = newUsers
        notifyDataSetChanged()
    }

    class UserViewHolder(private val itemBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(user: User?) {
            user?.let {
                itemBinding.apply {
                    texViewUserName.text = it.name
                    texViewUserNickName.text = it.nickname
                    texViewUserFollowersCount.text = it.followers
                    texViewUserFollowingCount.text = it.following
                }
            }
        }
    }
}