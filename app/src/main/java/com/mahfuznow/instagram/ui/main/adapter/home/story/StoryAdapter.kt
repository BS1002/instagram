package com.mahfuznow.instagram.ui.main.adapter.home.story

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mahfuznow.instagram.data.model.UsersData

class StoryAdapter constructor(
    users: ArrayList<UsersData.Data>
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(StoryAddDelegate())
        delegatesManager.addDelegate(StoryUserDelegate(users))
    }
}