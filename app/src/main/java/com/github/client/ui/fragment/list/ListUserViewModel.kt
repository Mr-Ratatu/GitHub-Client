package com.github.client.ui.fragment.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.client.model.UserListItem
import com.github.client.repository.UserListRepository
import com.github.client.utils.Constance.Companion.TAG
import com.github.client.utils.ScreenState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListUserViewModel(private val repository: UserListRepository) : ViewModel() {

    val screenStateViewModel = MutableLiveData<ScreenState>()
    private val userList = MutableLiveData<List<UserListItem>>()
    private val disposable = CompositeDisposable()

    fun getListUser() : MutableLiveData<List<UserListItem>> {
        screenStateViewModel.value = ScreenState.LOADING
        disposable += repository.getListUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    userList.postValue(list)
                    setScreenState(list)
                    Log.d(TAG, "getListUser: successful")
                },
                onError = {
                    it.printStackTrace()
                    screenStateViewModel.postValue(ScreenState.RESULT_ERROR)
                }
            )

        return userList
    }

    private fun setScreenState(userList: List<UserListItem>) {
        val screenState = if (userList.isNotEmpty())
            ScreenState.RESULT_OK
        else
            ScreenState.EMPTY

        screenStateViewModel.value = screenState
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}