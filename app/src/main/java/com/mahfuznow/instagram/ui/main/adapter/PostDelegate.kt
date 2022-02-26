package com.mahfuznow.instagram.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.databinding.ItemPostBinding
import com.mahfuznow.instagram.ui.base.DoubleClickListener
import javax.inject.Inject

class PostDelegate @Inject constructor() : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is PostsData.Data

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        PostViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position]
        item as PostsData.Data
        holder as PostViewHolder

        holder.binding.run {
            post = item //Data binding

            favourite.setOnClickListener { holder.toggleFavouriteIcon() }
            image.setOnClickListener(
                object : DoubleClickListener() {
                    override fun onDoubleClick(v: View?) {
                        holder.toggleFavouriteIcon()
                    }
                }
            )

            var toggleFollow = false
            follow.apply {
                setOnClickListener {
                    toggleFollow = !toggleFollow
                    text = if (toggleFollow) {
                        context.getString(R.string.following)
                    } else {
                        context.getString(R.string.follow)
                    }
                }
            }
        }

    }

    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        var toggleFavourite = false
        fun toggleFavouriteIcon() {
            toggleFavourite = !toggleFavourite
            binding.favourite.background = if (toggleFavourite) {
                ContextCompat.getDrawable(context, R.drawable.ic_favorite_filled)
            } else {
                ContextCompat.getDrawable(context, R.drawable.ic_favorite)
            }
        }
    }
}