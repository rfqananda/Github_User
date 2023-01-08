package com.rifqiananda.githubuserv2.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rifqiananda.githubuserv2.data.local.FavoriteDao
import com.rifqiananda.githubuserv2.data.local.FavoriteUser
import com.rifqiananda.githubuserv2.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var userDb: UserDatabase?


    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()

    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}