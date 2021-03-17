package com.github.client.extension

import android.view.View
import com.github.client.common.utils.ScreenState
import com.github.client.common.utils.ScreenState.*

fun View.emptyList(state: ScreenState) {
    visibility = when (state) {
        EMPTY -> View.VISIBLE
        else -> View.GONE
    }
}

fun View.connection(state: ScreenState) {
    visibility = when (state) {
        RESULT_ERROR -> View.VISIBLE
        else -> View.GONE
    }
}

fun View.chip(language: String?) {
    visibility = when {
        language.isNullOrEmpty() -> View.GONE
        else -> View.VISIBLE
    }
}

fun View.loading(state: ScreenState) {
    visibility = when (state) {
        LOADING -> View.VISIBLE
        else -> View.GONE
    }
}

fun View.profile(state: ScreenState) {
    visibility = when (state) {
        RESULT_OK -> View.VISIBLE
        else -> View.GONE
    }
}