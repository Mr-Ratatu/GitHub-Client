package com.github.client.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.client.repository.ProfileRepository
import com.github.client.repository.UserListRepository
import com.github.client.ui.fragment.list.ListUserViewModel
import com.github.client.ui.fragment.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListUserViewModel::class.java) -> ListUserViewModel(repository as UserListRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as ProfileRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}