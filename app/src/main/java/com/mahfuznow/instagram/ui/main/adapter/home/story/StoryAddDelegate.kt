package com.mahfuznow.instagram.ui.main.adapter.home.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.data.model.StoryAdd
import com.mahfuznow.instagram.databinding.ItemStoryAddBinding
import javax.inject.Inject

class StoryAddDelegate @Inject constructor() : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is StoryAdd

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        StoryAddViewHolder(
            ItemStoryAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position]
        item as StoryAdd
        holder as StoryAddViewHolder
    }

    class StoryAddViewHolder(val binding: ItemStoryAddBinding) : RecyclerView.ViewHolder(binding.root)
}