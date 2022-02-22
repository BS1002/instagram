package com.mahfuznow.instagram.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class HomeListDelegationAdapter @Inject constructor(
    private val photoAdapterDelegate: PhotoAdapterDelegate
) : ListDelegationAdapter<ArrayList<Any>>() {

    init {
        delegatesManager.addDelegate(photoAdapterDelegate)
    }
}