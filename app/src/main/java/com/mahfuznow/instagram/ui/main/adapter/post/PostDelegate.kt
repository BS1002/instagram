package com.mahfuznow.instagram.ui.main.adapter.post

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.databinding.ItemPostBinding
import com.mahfuznow.instagram.ui.base.OnSingleDoubleClickListener
import com.mahfuznow.instagram.ui.main.view.HomeFragmentDirections
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

        //Data binding
        holder.binding.post = item

        holder.onBind(item, position)
    }

    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Recycler View recycled it's views to reduce memory consumption
         * But that causes duplicate View states (For example if we check an checkbox
         * on one item, this state will reflect on another item as the previous view is reused)
         * We can prevent this by storing CheckState of each position in a (int -> bool) map
         * in OnClickListener and restoring from it to render Proper checkState
         *
         * In real case scenario this states should be fetched from data source via DataModel
         * In that case we can store those states in corresponding data Model
         */
        private val favouriteStates = SparseBooleanArray()
        private val bookmarkStates = SparseBooleanArray()
        private val followStates = SparseBooleanArray()

        private val context = binding.root.context
        private val favourite = binding.favourite
        private val bookmark = binding.bookmark
        private val follow = binding.follow
        private val image = binding.image
        private val imageHeart = binding.imageHeart

        fun onBind(item: PostsData.Data, position: Int) {

            //Restore checked state when view is recycled
            favourite.isChecked = favouriteStates.get(position)
            bookmark.isChecked = bookmarkStates.get(position)
            follow.isChecked = followStates.get(position)
            setFollowText()

            image.setOnClickListener(
                object : OnSingleDoubleClickListener() {
                    override fun onDoubleClick(view: View) {
                        favourite.toggle()
                        animateHeart(imageHeart)
                    }

                    override fun onSingleClick(view: View) {
                        view.findNavController().navigate(HomeFragmentDirections.actionHomeToPostDetailsFragment(item))
                    }
                }
            )

            follow.run {
                setOnClickListener {
                    followStates.put(position, isChecked)
                    setFollowText()
                }
            }

            favourite.run {
                setOnClickListener {
                    favouriteStates.put(position, isChecked)
                }
            }
            bookmark.run {
                setOnClickListener {
                    bookmarkStates.put(position, isChecked)
                    if (isChecked) {
                        Snackbar.make(it, "Saved as a collection", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        private fun setFollowText() {
            follow.run {
                text = if (isChecked) {
                    context.getString(R.string.following)
                } else {
                    context.getString(R.string.follow)
                }
            }
        }

        fun animateHeart(view: ImageView) {
            val scaleAnimation = ScaleAnimation(
                0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            prepareAnimation(scaleAnimation)
            val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
            prepareAnimation(alphaAnimation)
            val animation = AnimationSet(true)
            animation.addAnimation(alphaAnimation)
            animation.addAnimation(scaleAnimation)
            animation.duration = 700
            animation.fillAfter = true
            view.startAnimation(animation)
        }

        private fun prepareAnimation(animation: Animation): Animation {
            animation.repeatCount = 1
            animation.repeatMode = Animation.REVERSE
            return animation
        }
    }
}