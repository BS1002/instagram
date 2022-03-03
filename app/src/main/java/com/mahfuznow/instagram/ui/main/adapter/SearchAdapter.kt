package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mahfuznow.instagram.ui.main.adapter.post.ProfilePostDelegate
import com.mahfuznow.instagram.ui.main.adapter.post.SearchPostDelegate
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val searchPostDelegate: SearchPostDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {
    init {
        delegatesManager.addDelegate(searchPostDelegate)
    }
}