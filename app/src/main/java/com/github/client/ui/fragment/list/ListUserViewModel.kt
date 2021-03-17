package com.github.client.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.client.data.model.UserListItem
import com.github.client.data.repository.UserListRepository
import com.github.client.common.utils.ScreenState
import com.github.client.common.utils.getScreenState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ListUserViewModel(private val repository: UserListRepository) : ViewModel() {

    var userSearchField = ""
    val screenStateViewModel = MutableLiveData(ScreenState.LOADING)

    val userSearchList = MutableLiveData<List<UserListItem>>()

    private val _userList = MutableLiveData<List<UserListItem>>()
    val userList: LiveData<List<UserListItem>>
        get() = _userList

    private val disposable = CompositeDisposable()

    init {
        getListUser()
    }

    private fun getListUser() {
        screenStateViewModel.value = ScreenState.LOADING
        disposable += repository.getListUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    _userList.postValue(list)
                    screenStateViewModel.value = getScreenState(list)
                },
                onError = {
                    it.printStackTrace()
                    screenStateViewModel.postValue(ScreenState.RESULT_ERROR)
                }
            )
    }

    fun requestSearchUser(username: String) {
        screenStateViewModel.postValue(ScreenState.LOADING)
        disposable += repository.getSearchUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    userSearchList.postValue(list.userList)
                    screenStateViewModel.value = getScreenState(list.userList)
                },
                onError = {
                    it.printStackTrace()
                    screenStateViewModel.postValue(ScreenState.RESULT_ERROR)
                }
            )
    }

    fun searchUsers(username: String) {
        requestSearchUser(username)
    }

    fun refreshList() {
        getListUser()
    }

    fun listEmpty() {
        screenStateViewModel.postValue(ScreenState.EMPTY)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}