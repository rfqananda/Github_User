package com.rifqiananda.githubuserv2.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rifqiananda.githubuserv2.R
import com.rifqiananda.githubuserv2.adapter.FollowAdapter
import com.rifqiananda.githubuserv2.databinding.FragmentFollowBinding
import com.rifqiananda.githubuserv2.ui.view.FollowingViewModel

class FollowingFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUser.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}