package com.github.client.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.client.R
import com.github.client.extension.*
import com.github.client.utils.ScreenState
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("adapter")
fun setRecyclerAdapter(recyclerView: RecyclerView, userAdapter: RecyclerView.Adapter<*>) {
    recyclerView.apply {
        adapter = userAdapter
    }
}

@BindingAdapter("userAvatar")
fun setUserAvatar(imageView: CircleImageView, avatarUrl: String?) {
    Glide.with(imageView)
        .load(avatarUrl)
        .placeholder(R.drawable.ic_avatar_placeholder)
        .into(imageView)
}

@BindingAdapter("checkOnEmpty")
fun setInternetError(view: View, str: String?) {
    view.chip(str)
}

@BindingAdapter("noInternetConnection")
fun setInternetError(view: View, state: ScreenState) {
    view.connection(state)
}

@BindingAdapter("loading")
fun setLoading(progress: View, state: ScreenState) {
    progress.loading(state)
}

@BindingAdapter("profileUser")
fun setProfile(view: View, state: ScreenState) {
    view.profile(state)
}

@BindingAdapter("emptyList")
fun setEmptyList(view: View, state: ScreenState) {
    view.emptyList(state)
}