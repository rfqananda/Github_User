package com.rifqiananda.githubuserv2.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqiananda.githubuserv2.R
import com.rifqiananda.githubuserv2.adapter.UserAdapter
import com.rifqiananda.githubuserv2.data.local.FavoriteUser
import com.rifqiananda.githubuserv2.data.model.User
import com.rifqiananda.githubuserv2.databinding.FragmentFavoriteBinding
import com.rifqiananda.githubuserv2.databinding.FragmentFollowBinding
import com.rifqiananda.githubuserv2.ui.detail.DetailUser
import com.rifqiananda.githubuserv2.ui.view.FavoriteViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClick(object: UserAdapter.OnAdapterListener{
            override fun onClick(userData: User) {
                Intent(context, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, userData.login)
                    it.putExtra(DetailUser.EXTRA_ID, userData.id.toInt())
                    it.putExtra(DetailUser.EXTRA_AVA, userData.avatar)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(context)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(viewLifecycleOwner) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User>{
        val listUsers = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id.toString(),
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}