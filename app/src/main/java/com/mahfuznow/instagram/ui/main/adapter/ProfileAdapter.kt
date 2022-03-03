package com.mahfuznow.instagram.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mahfuznow.instagram.ui.main.adapter.post.ProfilePostDelegate
import com.mahfuznow.instagram.ui.main.adapter.post.SearchPostDelegate
import javax.inject.Inject

class ProfileAdapter @Inject constructor(
    private val profilePostDelegate: ProfilePostDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {
    init {
        delegatesManager.addDelegate(profilePostDelegate)
    }
}