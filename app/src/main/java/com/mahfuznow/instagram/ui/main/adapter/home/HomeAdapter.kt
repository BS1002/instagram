package com.mahfuznow.instagram.ui.main.adapter.home

import android.widget.Filter
import android.widget.Filterable
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mahfuznow.instagram.data.model.PostsData
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    homeStoryDelegate: HomeStoryDelegate,
    homePostDelegate: HomePostDelegate
) : ListDelegationAdapter<ArrayList<Any>>(), Filterable {

    private var allItems: ArrayList<Any> = ArrayList()

    init {
        delegatesManager.addDelegate(homeStoryDelegate)
        delegatesManager.addDelegate(homePostDelegate)
    }

    //backing up full list by copying it. because `items` will be changing by search
    override fun setItems(items: ArrayList<Any>) {
        super.setItems(items)
        allItems = ArrayList(items)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString().lowercase()
                //Log.d("test", "Query: $charString")

                var itemsFiltered: ArrayList<Any> = ArrayList()
                if (charString.isEmpty()) {
                    itemsFiltered.addAll(allItems)
                } else {
                    val matchedList: ArrayList<Any> = ArrayList()
                    for (item in allItems) {
                        when (item) {
                            is PostsData.Data -> {
                                if (item.owner.firstName.lowercase().contains(charSequence)) {
                                    matchedList.add(item)
                                }
                            }
                        }
                    }
                    itemsFiltered.clear()
                    itemsFiltered = matchedList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                // refresh the list with filtered data
                items = filterResults.values as ArrayList<Any>
                notifyDataSetChanged()
            }
        }
    }
}