package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.ui.main.adapter.story.StoryAddDelegate
import com.mahfuznow.instagram.ui.main.adapter.story.StoryUserDelegate

class StoryAdapter constructor(
    users: ArrayList<UsersData.Data>
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(StoryAddDelegate())
        delegatesManager.addDelegate(StoryUserDelegate(users))
    }
}