package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class FeedDelegationAdapter @Inject constructor(
    private val storyListAdapterDelegate: StoryListAdapterDelegate,
    private val photoAdapterDelegate: PhotoAdapterDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(storyListAdapterDelegate)
        delegatesManager.addDelegate(photoAdapterDelegate)
    }
}