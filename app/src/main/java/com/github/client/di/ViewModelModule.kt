package com.github.client.di

import com.github.client.ui.fragment.list.ListUserViewModel
import com.github.client.ui.fragment.profile.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ListUserViewModel(get()) }

    viewModel { ProfileViewModel(get()) }

}