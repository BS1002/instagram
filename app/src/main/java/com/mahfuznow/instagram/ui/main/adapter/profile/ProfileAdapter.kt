package com.mahfuznow.instagram.ui.main.adapter.profile

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class ProfileAdapter @Inject constructor(
    private val profilePostDelegate: ProfilePostDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {
    init {
        delegatesManager.addDelegate(profilePostDelegate)
    }
}