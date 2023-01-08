package com.rifqiananda.githubuserv2.ui.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqiananda.githubuserv2.api.RetrofitClient
import com.rifqiananda.githubuserv2.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    private val api by lazy { RetrofitClient().endpoint }

    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String){
        api.getFollowers(username).enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful){
                    listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Failure", t.message!!)
            }

        })
    }

    fun getListFollowers(): LiveData<ArrayList<User>>{
        return listFollowers
    }
}