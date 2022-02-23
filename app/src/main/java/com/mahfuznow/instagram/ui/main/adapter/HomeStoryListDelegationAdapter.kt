package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class HomeStoryListDelegationAdapter @Inject constructor(
    private val userResultAdapterDelegate: UserResultAdapterDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(userResultAdapterDelegate)
    }
}