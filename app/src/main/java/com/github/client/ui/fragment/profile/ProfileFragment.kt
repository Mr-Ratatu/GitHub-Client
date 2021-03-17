package com.github.client.ui.fragment.profile

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.github.client.R
import com.github.client.common.base.BaseFragment
import com.github.client.databinding.FragmentProfileBinding
import com.github.client.ui.adapter.ReposListAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val reposListAdapter by lazy { ReposListAdapter() }
    private val args by navArgs<ProfileFragmentArgs>()
    private val profileViewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = extras
        sharedElementReturnTransition = extras
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initActionBar(activity as AppCompatActivity)

        binding.apply {
            adapter = reposListAdapter
            userList = args.userList
        }

        profileViewModel.apply {
            screenStateViewModel.observe(viewLifecycleOwner, {
                binding.state = it
            })

            getProfileData(args.userList.login).observe(viewLifecycleOwner, {
                binding.user = it
            })

            getReposItem(args.userList.login).observe(viewLifecycleOwner, {
                reposListAdapter.setReposData(it)
            })

        }
    }

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

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