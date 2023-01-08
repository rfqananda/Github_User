package com.rifqiananda.githubuserv2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rifqiananda.githubuserv2.data.model.User
import com.rifqiananda.githubuserv2.databinding.LayoutAdapterFollowerBinding


class FollowAdapter: RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    private val ListData = ArrayList<User>()

    fun setList(users: List<User>){
        ListData.clear()
        ListData.addAll(users)
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(val binding: LayoutAdapterFollowerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .circleCrop()
                    .into(ivFollowers)

                tvNameFollowers.text = user.login
                tvIdFollowers.text = "ID: ${user.id}"

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val view = LayoutAdapterFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(ListData[position])
    }

    override fun getItemCount(): Int = ListData.size
}