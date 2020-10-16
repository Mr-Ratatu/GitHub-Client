package com.github.client.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.client.R
import com.github.client.databinding.UserItemBinding
import com.github.client.model.UserListItem
import com.github.client.ui.fragment.list.ListUsersFragmentDirections

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UsersViewHolder>() {

    private val item = mutableListOf<UserListItem>()

    fun setData(list: List<UserListItem>) {
        item.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsersViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.apply {
            binding.item = item[position]
            itemView.setOnClickListener {
                val action = ListUsersFragmentDirections.actionListToProfile(item[position].login)
                it.findNavController().navigate(action)
            }
        }

    }

    class UsersViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

}