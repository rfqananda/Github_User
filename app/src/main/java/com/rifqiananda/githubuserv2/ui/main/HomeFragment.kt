package com.rifqiananda.githubuserv2.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqiananda.githubuserv2.R
import com.rifqiananda.githubuserv2.adapter.UserAdapter
import com.rifqiananda.githubuserv2.data.model.User
import com.rifqiananda.githubuserv2.databinding.FragmentHomeBinding
import com.rifqiananda.githubuserv2.ui.detail.DetailUser
import com.rifqiananda.githubuserv2.ui.view.MainViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : MainViewModel
    private lateinit var dataAdapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        dataAdapter = UserAdapter()
        dataAdapter.notifyDataSetChanged()

        dataAdapter.setOnItemClick(object: UserAdapter.OnAdapterListener{
            override fun onClick(userData: User) {
                Intent(context, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, userData.login)
                    it.putExtra(DetailUser.EXTRA_ID, userData.id.toInt())
                    it.putExtra(DetailUser.EXTRA_AVA, userData.avatar)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        binding.apply {

            rvUser.layoutManager = LinearLayoutManager(context)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = dataAdapter

            btnSearch.setOnClickListener {
                searchUser()
                closeKeyboard(btnSearch)
            }

            etSearchUser.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    closeKeyboard(etSearchUser)
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(viewLifecycleOwner) {
            if (it != null) {
                dataAdapter.setList(it)
                showLoading(false)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        showLoading(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun searchUser(){
        binding.apply {
            val query = etSearchUser.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun closeKeyboard(view: View){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}