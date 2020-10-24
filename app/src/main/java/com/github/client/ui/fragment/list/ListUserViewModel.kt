package com.github.client.ui.fragment.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.client.model.UserListItem
import com.github.client.repository.UserListRepository
import com.github.client.utils.ScreenState
import com.github.client.utils.getScreenState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ListUserViewModel(private val repository: UserListRepository) : ViewModel() {

    var userSearchField = ""
    val screenStateViewModel = MutableLiveData<ScreenState>()
    val userSearchList = MutableLiveData<List<UserListItem>>()
    private val userList = MutableLiveData<List<UserListItem>>()
    private val disposable = CompositeDisposable()

    fun getListUser(): MutableLiveData<List<UserListItem>> {
        screenStateViewModel.value = ScreenState.LOADING
        disposable += repository.getListUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    userList.postValue(list)
                    screenStateViewModel.value = getScreenState(list)
                },
                onError = {
                    it.printStackTrace()
                    screenStateViewModel.postValue(ScreenState.RESULT_ERROR)
                }
            )

        return userList
    }

    fun requestSearchUser(username: String) {
        screenStateViewModel.postValue(ScreenState.LOADING)
        disposable += repository.getSearchUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userList ->
                    userSearchList.postValue(userList)
                    screenStateViewModel.value = getScreenState(userList)
                },
                onError = {
                    it.printStackTrace()
                    screenStateViewModel.postValue(ScreenState.RESULT_ERROR)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}