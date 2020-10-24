package com.github.client.ui.fragment.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.client.model.UserProfile
import com.github.client.model.UserReposItem
import com.github.client.repository.ProfileRepository
import com.github.client.utils.ScreenState
import com.github.client.utils.ScreenState.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    val screenStateViewModel = MutableLiveData<ScreenState>()
    private val profile = MutableLiveData<UserProfile>()
    private val reposList = MutableLiveData<List<UserReposItem>>()
    private val disposable = CompositeDisposable()

    fun getProfileData(username: String): MutableLiveData<UserProfile> {
        screenStateViewModel.value = LOADING
        disposable += repository.getUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    profile.postValue(it)
                    setScreenState(it)
                },
                onError = {
                    it.printStackTrace()
                    screenStateViewModel.postValue(RESULT_ERROR)
                }
            )

        return profile
    }

    fun getReposItem(username: String): MutableLiveData<List<UserReposItem>> {
        screenStateViewModel.value = LOADING
        disposable += repository.getUserRepository(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    reposList.postValue(it)
                    setScreenState(it)
                },
                onError = {
                    it.printStackTrace()
                    screenStateViewModel.postValue(RESULT_ERROR)
                }
            )

        return reposList
    }

    private fun setScreenState(user: UserProfile?) {
        val screenState = if (user != null)
            RESULT_OK
        else
            EMPTY

        screenStateViewModel.value = screenState
    }

    private fun setScreenState(reposItem: List<UserReposItem>) {
        val screenState = if (reposItem.isNotEmpty())
            RESULT_OK
        else
            EMPTY

        screenStateViewModel.value = screenState
    }

}