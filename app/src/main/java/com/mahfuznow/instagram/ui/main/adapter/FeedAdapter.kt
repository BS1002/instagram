package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class FeedAdapter @Inject constructor(
    storyListDelegate: StoryListDelegate,
    postDelegate: PostDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(storyListDelegate)
        delegatesManager.addDelegate(postDelegate)
    }
}