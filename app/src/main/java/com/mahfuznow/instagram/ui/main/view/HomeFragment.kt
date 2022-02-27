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
import com.mahfuznow.instagram.data.model.HomeStory
import com.mahfuznow.instagram.databinding.FragmentHomeBinding
import com.mahfuznow.instagram.ui.main.adapter.home.HomeAdapter
import com.mahfuznow.instagram.ui.main.viewmodel.HomeFragmentViewModel
import com.mahfuznow.instagram.util.LoadingState
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
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.progressBar
        feedRecyclerView = binding.feedRecyclerView

        //items is a field defined in super class of the adapter
        homeAdapter.items = feedList

        feedRecyclerView.setHasFixedSize(true)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        feedRecyclerView.adapter = homeAdapter

        observeLiveData()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            isLoadedStory = false
            isLoadedFeed = false
            viewModel.reloadLiveData()
        }
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
    }


    private fun observeLiveData() {
        viewModel.users.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingState.Error -> onError(it.e, "Users")
                is LoadingState.Loading -> Log.d("test", "Users: Loading")
                is LoadingState.Success -> {
                    Log.d("test", "Users: Success")
                    isLoadedStory = true
                    this.storyList.clear()
                    this.storyList.addAll(it.data.data)
                    updateList()
                }
            }

        }
        viewModel.posts.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingState.Error -> onError(it.e, "Posts")
                is LoadingState.Loading -> Log.d("test", "Posts: Loading")
                is LoadingState.Success -> {
                    Log.d("test", "Posts: Success")
                    isLoadedFeed = true
                    this.feedList.clear()
                    this.feedList.addAll(it.data.data)
                    updateList()
                }
            }

        }

    }

    private fun onError(e: Throwable, msg: String) {
        progressBar.visibility = View.INVISIBLE
        swipeRefreshLayout.isRefreshing = false
        Log.d("test", "Failed to load data ${e.message} data")
        Toast.makeText(context, "Failed to load $msg data", Toast.LENGTH_SHORT).show()
    }

    private fun updateList() {
        if (isLoadedStory && isLoadedFeed) {
            Log.d("test", "updateList: ")
            val updatedItems = ArrayList<Any>()
            updatedItems.add(HomeStory(storyList))
            updatedItems.addAll(feedList)
            homeAdapter.items = updatedItems
            homeAdapter.notifyDataSetChanged()
            progressBar.visibility = View.INVISIBLE
            swipeRefreshLayout.isRefreshing = false
        }
    }
}