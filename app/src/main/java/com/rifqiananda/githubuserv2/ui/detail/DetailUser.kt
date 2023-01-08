package com.rifqiananda.githubuserv2.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rifqiananda.githubuserv2.adapter.SectionPagerAdapter
import com.rifqiananda.githubuserv2.databinding.ActivityDetailUserBinding
import com.rifqiananda.githubuserv2.ui.view.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVA = "extra_ava"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loading = LoadingDialog(this)

        val username = intent.getStringExtra(EXTRA_USERNAME)!!
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVA)!!
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)


        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        loading.startDialog()
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this, {

            if (it != null) {
                binding.apply {

                    if (it.name != null) {
                        tvNameDetail.text = it.name
                    } else {
                        tvNameDetail.text = " - "
                    }

                    if (it.login != null) {
                        tvUsernameDetail.text = "(${it.login})"
                    } else {
                        tvUsernameDetail.text = " - "
                    }

                    if (it.company != null) {
                        tvCompanyDetail.text = it.company
                    } else {
                        tvCompanyDetail.text = " - "
                    }

                    if (it.location != null) {
                        tvLocationDetail.text = it.location
                    } else {
                        tvLocationDetail.text = " - "
                    }

                    tvFollowers.text = "${it.followers} followers"
                    tvFollowing.text = "${it.following} following"

                    Glide.with(this@DetailUser)
                        .load(it.avatar_url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .circleCrop()
                        .into(ivDetail)

                    btnBack.setOnClickListener {
                        onBackPressed()
                    }
                }
                loading.isDismiss()
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addToFavorite(username, id, avatar)
            }else{
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
}