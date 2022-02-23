package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
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
import com.mahfuznow.instagram.ui.main.adapter.HomeFeedListDelegationAdapter
import com.mahfuznow.instagram.ui.main.adapter.HomeStoryListDelegationAdapter
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
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var feedRecyclerView: RecyclerView

    @Inject
    lateinit var storyAdapter: HomeStoryListDelegationAdapter

    @Inject
    lateinit var feedAdapter: HomeFeedListDelegationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.progressBar

        storyRecyclerView = binding.storyRecyclerView
        feedRecyclerView = binding.feedRecyclerView

        //items is a field defined in super class of the adapter
        storyAdapter.items = storyList
        feedAdapter.items = feedList

        storyRecyclerView.setHasFixedSize(true)
        storyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        storyRecyclerView.adapter = storyAdapter

        feedRecyclerView.setHasFixedSize(true)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        feedRecyclerView.adapter = feedAdapter

        observeLiveData()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            viewModel.reFetchData()
        }
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
    }


    private fun observeLiveData() {
        viewModel.userResults.observe(viewLifecycleOwner) {
            this.storyList.addAll(it)
            //Toast.makeText(this, "Data loaded successfully", Toast.LENGTH_SHORT).show()
            updateStoryList()
        }
        viewModel.isErrorUserLiveData.observe(viewLifecycleOwner) {
            if (it) onError("User")
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            this.feedList.addAll(it)
            //Toast.makeText(this, "Data loaded successfully", Toast.LENGTH_SHORT).show()
            updateFeedList()
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

    private fun updateStoryList() {
        storyAdapter.items = storyList
        storyAdapter.notifyDataSetChanged()
        progressBar.visibility = View.INVISIBLE
        swipeRefreshLayout.isRefreshing = false
    }

    private fun updateFeedList() {
        feedAdapter.items = feedList
        feedAdapter.notifyDataSetChanged()
        progressBar.visibility = View.INVISIBLE
        swipeRefreshLayout.isRefreshing = false
    }
}