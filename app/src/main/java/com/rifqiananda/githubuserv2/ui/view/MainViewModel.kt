package com.rifqiananda.githubuserv2.ui.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqiananda.githubuserv2.api.RetrofitClient
import com.rifqiananda.githubuserv2.data.model.User
import com.rifqiananda.githubuserv2.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    private val api by lazy { RetrofitClient().endpoint }

    fun setSearchUsers(query : String){

        api.getSearchUsers(query).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    listUsers.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("SearchError", t.toString())
            }
        })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }
}