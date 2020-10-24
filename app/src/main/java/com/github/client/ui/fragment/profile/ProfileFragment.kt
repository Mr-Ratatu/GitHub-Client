package com.github.client.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.github.client.R
import com.github.client.base.BaseFragment
import com.github.client.databinding.FragmentProfileBinding
import com.github.client.network.RetrofitInstance
import com.github.client.repository.ProfileRepository
import com.github.client.ui.adapter.ReposListAdapter
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileRepository>() {

    private val adapter = ReposListAdapter()
    private val args by navArgs<ProfileFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initActionBar(activity as AppCompatActivity)

        binding.adapter = adapter

        viewModel.apply {
            screenStateViewModel.observe(viewLifecycleOwner, {
                binding.state = it
            })

            getProfileData(args.username).observe(viewLifecycleOwner, {
                binding.user = it
            })

            getReposItem(args.username).observe(viewLifecycleOwner, {
                adapter.setReposData(it)
            })

        }

    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getRepository() = ProfileRepository(RetrofitInstance.api)

    private fun initActionBar(activity: AppCompatActivity) {
        activity.setSupportActionBar(profileToolbar)
        val actionBar = activity.supportActionBar
        actionBar?.run {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        profileToolbar.run {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                activity.onBackPressed()
            }
        }
    }

}