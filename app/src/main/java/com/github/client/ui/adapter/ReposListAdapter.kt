package com.github.client.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.client.R
import com.github.client.databinding.RepositoryItemBinding
import com.github.client.data.model.UserReposItem

class ReposListAdapter : RecyclerView.Adapter<ReposListAdapter.ReposListViewHolder>() {

    private val item = mutableListOf<UserReposItem>()

    fun setReposData(list: List<UserReposItem>) {
        item.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReposListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.repository_item,
                parent,
                false
            )
        )

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ReposListViewHolder, position: Int) {
        holder.binding.item = item[position]
    }

    class ReposListViewHolder(val binding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}