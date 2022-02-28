package com.mahfuznow.instagram.ui.main.adapter.search

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val searchPostDelegate: SearchPostDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {
    init {
        delegatesManager.addDelegate(searchPostDelegate)
    }
}