package com.mahfuznow.instagram.ui.main.adapter.home

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.data.model.HomeStory
import com.mahfuznow.instagram.data.model.StoryAdd
import com.mahfuznow.instagram.databinding.ItemStoryListBinding
import com.mahfuznow.instagram.ui.main.adapter.home.story.StoryAdapter
import javax.inject.Inject

class HomeStoryDelegate @Inject constructor(
    private val application: Application,
    private val storyAdapter: StoryAdapter
) : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is HomeStory

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        StoryListViewHolder(
            ItemStoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val story = items[position]
        story as HomeStory
        holder as StoryListViewHolder

        val storyItems = ArrayList<Any>()
        storyItems.add(StoryAdd())
        storyItems.addAll(story.items)

        //items is a field defined in super class of the adapter
        storyAdapter.items = storyItems

        with(holder.binding) {
            storyRecyclerView.setHasFixedSize(true)
            storyRecyclerView.layoutManager = LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
            storyRecyclerView.adapter = storyAdapter
        }
    }

    class StoryListViewHolder(val binding: ItemStoryListBinding) : RecyclerView.ViewHolder(binding.root)
}