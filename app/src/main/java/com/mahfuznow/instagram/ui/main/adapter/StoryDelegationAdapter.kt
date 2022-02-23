package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class StoryDelegationAdapter @Inject constructor(
    private val userAdapterDelegate: UserAdapterDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(userAdapterDelegate)
    }
}