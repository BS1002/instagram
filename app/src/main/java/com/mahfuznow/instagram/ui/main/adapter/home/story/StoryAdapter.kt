package com.mahfuznow.instagram.ui.main.adapter.home.story

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class StoryAdapter @Inject constructor(
    private val storyAddDelegate: StoryAddDelegate,
    private val storyUserDelegate: StoryUserDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(storyAddDelegate)
        delegatesManager.addDelegate(storyUserDelegate)
    }
}