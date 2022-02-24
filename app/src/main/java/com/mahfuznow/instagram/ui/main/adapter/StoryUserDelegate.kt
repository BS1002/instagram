package com.mahfuznow.instagram.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.databinding.ItemStoryUserBinding
import javax.inject.Inject

class StoryUserDelegate @Inject constructor() : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is UsersData.Data

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        StoryUserViewHolder(
            ItemStoryUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position]
        item as UsersData.Data
        holder as StoryUserViewHolder

        holder.binding.user = item
    }

    class StoryUserViewHolder(val binding: ItemStoryUserBinding) : RecyclerView.ViewHolder(binding.root)
}