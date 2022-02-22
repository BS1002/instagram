package com.mahfuznow.instagram.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.databinding.ItemImageBinding
import com.mahfuznow.instagram.model.Photo
import javax.inject.Inject

class PhotoAdapterDelegate @Inject constructor() : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is Photo

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        PhotoViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val photo = items[position]
        photo as Photo
        holder as PhotoViewHolder

        holder.binding.photo = photo
    }

    class PhotoViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}