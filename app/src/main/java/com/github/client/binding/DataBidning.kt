package com.github.client.binding

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.client.R
import com.github.client.extension.chip
import com.github.client.extension.connection
import com.github.client.extension.loading
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

@BindingAdapter("languageCheck")
fun setInternetError(view: View, language: String?) {
    view.chip(language)
}

@BindingAdapter("noInternetConnection")
fun setInternetError(view: View, state: ScreenState) {
    view.connection(state)
}

@BindingAdapter("loading")
fun setLoading(progress: View, state: ScreenState) {
    progress.loading(state)
}