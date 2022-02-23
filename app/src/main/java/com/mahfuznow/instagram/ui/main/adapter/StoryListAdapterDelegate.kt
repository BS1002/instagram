package com.mahfuznow.instagram.ui.main.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.data.model.StoryList
import com.mahfuznow.instagram.databinding.ItemStoryListBinding
import javax.inject.Inject

class StoryListAdapterDelegate @Inject constructor(
    private val application: Application,
    private val storyDelegationAdapter: StoryDelegationAdapter
) : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is StoryList

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        StoryListViewHolder(
            ItemStoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val story = items[position]
        story as StoryList
        holder as StoryListViewHolder

        val storyItems = ArrayList<Any>()
        storyItems.addAll(story.items)

        //items is a field defined in super class of the adapter
        storyDelegationAdapter.items = storyItems

        with(holder.binding) {
            storyRecyclerView.setHasFixedSize(true)
            storyRecyclerView.layoutManager = LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
            storyRecyclerView.adapter = storyDelegationAdapter
        }
    }

    class StoryListViewHolder(val binding: ItemStoryListBinding) : RecyclerView.ViewHolder(binding.root)
}