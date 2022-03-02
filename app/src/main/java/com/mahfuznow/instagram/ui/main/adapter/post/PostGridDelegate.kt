package com.mahfuznow.instagram.ui.main.adapter.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.databinding.ItemPostGridBinding
import com.mahfuznow.instagram.ui.main.view.ProfileFragmentDirections
import javax.inject.Inject


class PostGridDelegate @Inject constructor() : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is PostsData.Data

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ProfilePostViewHolder(
            ItemPostGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position]
        item as PostsData.Data
        holder as ProfilePostViewHolder

        //Data binding
        holder.binding.post = item

        holder.binding.root.setOnClickListener {
            it.findNavController().navigate(ProfileFragmentDirections.actionProfileToPostDetailsFragment(item))
        }
    }

    class ProfilePostViewHolder(val binding: ItemPostGridBinding) : RecyclerView.ViewHolder(binding.root)
}