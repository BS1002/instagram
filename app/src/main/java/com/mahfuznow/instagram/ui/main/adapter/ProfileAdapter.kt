package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mahfuznow.instagram.ui.main.adapter.post.PostGridDelegate
import javax.inject.Inject

class ProfileAdapter @Inject constructor(
    private val postGridDelegate: PostGridDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {
    init {
        delegatesManager.addDelegate(postGridDelegate)
    }
}