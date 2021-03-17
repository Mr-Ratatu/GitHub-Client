package com.github.client.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.client.R
import com.github.client.databinding.UserItemBinding
import com.github.client.data.model.UserListItem
import com.github.client.ui.fragment.list.ListUsersFragmentDirections

class UserListAdapter : ListAdapter<UserListItem, UserListAdapter.UsersViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsersViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.binding.apply {
            item = getItem(position)
            userCard.setOnClickListener {
                val action =
                    ListUsersFragmentDirections.actionListToProfile(getItem(position))
                it.findNavController().navigate(
                    action,
                    FragmentNavigatorExtras(profileImage to "profile", login to "login")
                )
            }
        }
    }

    class UsersViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<UserListItem>() {
            override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}