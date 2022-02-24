package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class StoryAdapter @Inject constructor(
    private val storyUserDelegate: StoryUserDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(storyUserDelegate)
    }
}