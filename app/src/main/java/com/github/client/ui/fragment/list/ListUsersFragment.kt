package com.github.client.ui.fragment.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.client.base.BaseFragment
import com.github.client.databinding.FragmentListUsersBinding
import com.github.client.network.RetrofitInstance
import com.github.client.repository.UserListRepository
import com.github.client.ui.adapter.UserListAdapter
import com.github.client.utils.Constance.Companion.TAG
import kotlinx.android.synthetic.main.fragment_list_users.*

class ListUsersFragment :
    BaseFragment<ListUserViewModel, FragmentListUsersBinding, UserListRepository>() {

    private val userListAdapter = UserListAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        binding.apply {
            adapter = userListAdapter
        }

        viewModel.apply {

            screenStateViewModel.observe(viewLifecycleOwner, Observer {
                binding.state = it
            })

            getListUser().observe(viewLifecycleOwner, Observer {
                userListAdapter.setData(it)
                Log.d(TAG, "onActivityCreated: successful")
            })
        }
    }

    override fun getViewModel() = ListUserViewModel::class.java

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListUsersBinding.inflate(inflater, container, false)

    override fun getRepository() = UserListRepository(RetrofitInstance.api)



}