package com.rifqiananda.githubuserv2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rifqiananda.githubuserv2.data.model.User
import com.rifqiananda.githubuserv2.databinding.FragmentThemeBinding
import com.rifqiananda.githubuserv2.databinding.LayoutAdapterBinding
import com.rifqiananda.githubuserv2.ui.themes.ThemeFragment

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val ListData = ArrayList<User>()

    private var onItemClick: OnAdapterListener? = null


    fun setOnItemClick(onItemClick: OnAdapterListener){
        this.onItemClick = onItemClick
    }

    fun setList(users: List<User>){
        ListData.clear()
        ListData.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: LayoutAdapterBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){


            binding.btnDetail.setOnClickListener {
                onItemClick?.onClick(user)
            }
            binding.apply {

                Glide.with(itemView)
                        .load(user.avatar)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .circleCrop()
                        .into(ivUser)

                tvName.text = user.login
                tvId.text = "ID: ${user.id}"


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(ListData[position])
    }

    override fun getItemCount(): Int = ListData.size

    interface OnAdapterListener{
        fun onClick(userData: User)
    }
}