package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mahfuznow.instagram.ui.main.adapter.post.PostStaggeredDelegate
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val postStaggeredDelegate: PostStaggeredDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {
    init {
        delegatesManager.addDelegate(postStaggeredDelegate)
    }
}