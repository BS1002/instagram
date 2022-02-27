package com.mahfuznow.instagram.ui.main.adapter.home

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    homeStoryDelegate: HomeStoryDelegate,
    homePostDelegate: HomePostDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(homeStoryDelegate)
        delegatesManager.addDelegate(homePostDelegate)
    }
}