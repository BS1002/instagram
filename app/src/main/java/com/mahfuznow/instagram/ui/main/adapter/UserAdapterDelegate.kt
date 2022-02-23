package com.mahfuznow.instagram.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.databinding.ItemStoryUserBinding
import com.mahfuznow.instagram.data.model.user.Result
import javax.inject.Inject

class UserAdapterDelegate @Inject constructor() : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is Result

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        PhotoViewHolder(
            ItemStoryUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val userResult = items[position]
        userResult as Result
        holder as PhotoViewHolder

        holder.binding.userResult = userResult
    }

    class PhotoViewHolder(val binding: ItemStoryUserBinding) : RecyclerView.ViewHolder(binding.root)
}