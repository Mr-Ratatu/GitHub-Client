package com.github.client.ui.fragment.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.github.client.R
import com.github.client.common.base.BaseFragment
import com.github.client.databinding.FragmentListUsersBinding
import com.github.client.ui.adapter.UserListAdapter
import com.github.client.common.utils.Constance.Companion.MIN_USER_QUERY_LENGTH
import com.github.client.common.utils.Constance.Companion.QUERY_DELAY
import com.github.client.common.utils.ScreenState.*
import com.github.client.extension.waitForTransition
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_list_users.*
import java.util.concurrent.TimeUnit
import org.koin.android.viewmodel.ext.android.viewModel

class ListUsersFragment :
    BaseFragment<FragmentListUsersBinding>() {

    private val listUserViewModel by viewModel<ListUserViewModel>()

    private val userListAdapter = UserListAdapter()
    private val disposable = CompositeDisposable()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        binding.apply {
            adapter = userListAdapter
            state = LOADING
            waitForTransition(recyclerView)
        }

        listUserViewModel.apply {

            screenStateViewModel.observe(viewLifecycleOwner, {
                binding.state = it
            })

            userList.observe(viewLifecycleOwner, {
                userListAdapter.submitList(it)
            })

            userSearchList.observe(viewLifecycleOwner, {
                userListAdapter.submitList(it)
            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.userlist_menu, menu)

        val searchMenu = menu.findItem(R.id.menu_action_search)
        val searchView = searchMenu?.actionView as SearchView

        observeSearchUsers(searchView)
        checkSearchViewOnEmptyQuery(searchMenu, searchView)
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
                    if (username.length > MIN_USER_QUERY_LENGTH) {
                        listUserViewModel.userSearchField = username
                        listUserViewModel.requestSearchUser(username)
                    } else {
                        listUserViewModel.userSearchList.postValue(emptyList())
                        listUserViewModel.screenStateViewModel.postValue(EMPTY)
                    }
                }
            )
    }

    private fun checkSearchViewOnEmptyQuery(menuItem: MenuItem, searchView: SearchView) {
        val searchField = listUserViewModel.userSearchField
        if (searchField.isNotEmpty()) {
            menuItem.expandActionView()
            searchView.setQuery(listUserViewModel.userSearchField, false)
        }
    }

    override fun onDestroyOptionsMenu() {
        disposable.clear()
        super.onDestroyOptionsMenu()
    }

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListUsersBinding.inflate(inflater, container, false)

}