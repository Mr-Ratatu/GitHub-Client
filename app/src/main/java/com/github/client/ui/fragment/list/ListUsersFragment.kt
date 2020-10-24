package com.github.client.ui.fragment.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.github.client.R
import com.github.client.base.BaseFragment
import com.github.client.databinding.FragmentListUsersBinding
import com.github.client.network.RetrofitInstance
import com.github.client.repository.UserListRepository
import com.github.client.ui.adapter.UserListAdapter
import com.github.client.utils.Constance.Companion.MIN_USER_QUERY_LENGTH
import com.github.client.utils.Constance.Companion.QUERY_DELAY
import com.github.client.utils.Constance.Companion.TAG
import com.github.client.utils.ScreenState.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_list_users.*
import java.util.concurrent.TimeUnit

class ListUsersFragment :
    BaseFragment<ListUserViewModel, FragmentListUsersBinding, UserListRepository>() {

    private var searchMenu: MenuItem? = null
    private var searchView: SearchView? = null
    private val userListAdapter = UserListAdapter()
    private val disposable = CompositeDisposable()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        binding.apply {
            adapter = userListAdapter
        }

        viewModel.apply {

            screenStateViewModel.observe(viewLifecycleOwner, {
                binding.state = it
            })

            getListUser().observe(viewLifecycleOwner, {
                userListAdapter.setData(it)
                Log.d(TAG, "onActivityCreated: successful")
            })

            userSearchList.observe(viewLifecycleOwner, {
                userListAdapter.setData(it)
            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.userlist_menu, menu)

        searchMenu = menu.findItem(R.id.menu_action_search)
        searchView = searchMenu?.actionView as SearchView

        observeSearchUsers(searchView!!)
        checkSearchViewOnEmptyQuery(searchMenu!!, searchView!!)
    }

    private fun observeSearchUsers(searchView: SearchView) {
        disposable += Observable.create<String> {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(usernameBegin: String): Boolean {
                    searchView.clearFocus()
                    it.onNext(usernameBegin)
                    return true
                }

                override fun onQueryTextChange(usernameBegin: String): Boolean {
                    it.onNext(usernameBegin)
                    return true
                }
            })
        }.debounce(QUERY_DELAY, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeBy(
                onNext = { username ->
                    if (username.length < MIN_USER_QUERY_LENGTH) {
                        viewModel.userSearchField = username
                        viewModel.requestSearchUser(username)
                    } else {
                        viewModel.userSearchList.postValue(emptyList())
                        viewModel.screenStateViewModel.postValue(EMPTY)
                    }
                }
            )
    }

    private fun checkSearchViewOnEmptyQuery(menuItem: MenuItem, searchView: SearchView) {
        val searchField = viewModel.userSearchField
        if (searchField.isNotEmpty()) {
            menuItem.expandActionView()
            searchView.setQuery(viewModel.userSearchField, false)
        }
    }

    override fun onDestroyOptionsMenu() {
        disposable.clear()
        super.onDestroyOptionsMenu()
    }

    override fun getViewModel() = ListUserViewModel::class.java

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListUsersBinding.inflate(inflater, container, false)

    override fun getRepository() = UserListRepository(RetrofitInstance.api)

}