package com.rifqiananda.githubuserv2.ui.view

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqiananda.githubuserv2.api.RetrofitClient
import com.rifqiananda.githubuserv2.data.local.FavoriteDao
import com.rifqiananda.githubuserv2.data.local.FavoriteUser
import com.rifqiananda.githubuserv2.data.local.UserDatabase
import com.rifqiananda.githubuserv2.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    private val api by lazy { RetrofitClient().endpoint }

    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        api.getUserDetail(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                user.postValue(response.body())
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e("Failure", t.message!!)
            }
        })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    @SuppressLint("SuspiciousIndentation")
    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
        var user = FavoriteUser(
            username,
            id,
            avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }
    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}