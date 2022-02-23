package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mahfuznow.instagram.data.model.StoryList
import com.mahfuznow.instagram.ui.main.adapter.FeedDelegationAdapter
import com.mahfuznow.instagram.databinding.FragmentHomeBinding
import com.mahfuznow.instagram.ui.main.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private var storyList: ArrayList<Any> = ArrayList()
    private var feedList: ArrayList<Any> = ArrayList()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var swipeRefreshListener: SwipeRefreshLayout.OnRefreshListener
    private lateinit var feedRecyclerView: RecyclerView

    private var isLoadedStory = false
    private var isLoadedFeed = false

    @Inject
    lateinit var feedDelegationAdapter: FeedDelegationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.progressBar
        feedRecyclerView = binding.feedRecyclerView

        //items is a field defined in super class of the adapter
        feedDelegationAdapter.items = feedList

        feedRecyclerView.setHasFixedSize(true)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        feedRecyclerView.adapter = feedDelegationAdapter

        observeLiveData()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            isLoadedStory = false
            isLoadedFeed = false
            viewModel.reFetchData()
        }
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
    }


    private fun observeLiveData() {
        viewModel.userResults.observe(viewLifecycleOwner) {
            Log.d("test", "observeLiveData: user")
            isLoadedStory = true
            this.storyList.addAll(it)
            updateList()
        }
        viewModel.isErrorUserLiveData.observe(viewLifecycleOwner) {
            if (it) onError("User")
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            Log.d("test", "observeLiveData: photo")
            isLoadedFeed = true
            this.feedList.addAll(it)
            updateList()
        }
        viewModel.isErrorPhotoLiveData.observe(viewLifecycleOwner) {
            if (it) onError("Photo")
        }

    }

    private fun onError(msg: String) {
        progressBar.visibility = View.INVISIBLE
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(context, "Failed to load data $msg's data", Toast.LENGTH_SHORT).show()
    }

    private fun updateList() {
        if (isLoadedStory && isLoadedFeed) {
            Log.d("test", "updateList: ")
            val updatedItems = ArrayList<Any>()
            updatedItems.add(StoryList(storyList))
            updatedItems.addAll(feedList)
            feedDelegationAdapter.items = updatedItems
            feedDelegationAdapter.notifyDataSetChanged()
            progressBar.visibility = View.INVISIBLE
            swipeRefreshLayout.isRefreshing = false
        }
    }
}