package com.mahfuznow.instagram.ui.main.adapter.post

import android.content.Context
import android.content.Intent
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
import com.mahfuznow.instagram.databinding.ItemPostHomeBinding
import com.mahfuznow.instagram.ui.base.OnSingleDoubleClickListener
import com.mahfuznow.instagram.ui.main.view.HomeFragmentDirections
import javax.inject.Inject


class HomePostDelegate @Inject constructor() : AdapterDelegate<ArrayList<Any>>() {
    override fun isForViewType(items: ArrayList<Any>, position: Int): Boolean = items[position] is PostsData.Data

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        PostViewHolder(
            ItemPostHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(items: ArrayList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position]
        item as PostsData.Data
        holder as PostViewHolder

        //Data binding
        holder.binding.post = item

        holder.onBind(item, position)
    }

    class PostViewHolder(val binding: ItemPostHomeBinding) : RecyclerView.ViewHolder(binding.root) {

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

        val context: Context = binding.root.context

        fun onBind(item: PostsData.Data, position: Int) {
            binding.run {

                //Restore checked state when view is recycled
                favourite.isChecked = favouriteStates.get(position)
                bookmark.isChecked = bookmarkStates.get(position)
                follow.isChecked = followStates.get(position)
                setFollowText()

                ownerImage.setOnClickListener {
                    it.findNavController().navigate(HomeFragmentDirections.actionHomeToProfile(item.owner.id))
                }

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

                var toggleCommentLayout = false
                comment.setOnClickListener {
                    toggleCommentLayout = !toggleCommentLayout
                    if (toggleCommentLayout)
                        commentLayout.visibility = View.VISIBLE
                    else
                        commentLayout.visibility = View.GONE
                }
                commentPost.setOnClickListener { comment.performClick() }

                bookmark.run {
                    setOnClickListener {
                        bookmarkStates.put(position, isChecked)
                        if (isChecked) {
                            Snackbar.make(it, "Saved as a collection", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }

                share.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, item.image)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            }

        }

        private fun setFollowText() {
            binding.run {
                follow.run {
                    text = if (isChecked) {
                        context.getString(R.string.following)
                    } else {
                        context.getString(R.string.follow)
                    }
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