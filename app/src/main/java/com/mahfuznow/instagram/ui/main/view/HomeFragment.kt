package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.data.model.HomeStory
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.databinding.FragmentHomeBinding
import com.mahfuznow.instagram.ui.main.adapter.HomeAdapter
import com.mahfuznow.instagram.ui.main.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private var storyList: ArrayList<UsersData.Data> = ArrayList()
    private var feedList: ArrayList<Any> = ArrayList()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
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

        //Enabling Action Menu
        setHasOptionsMenu(true)

        shimmer = binding.shimmer
        feedRecyclerView = binding.feedRecyclerView

        //items is a field defined in super class of the adapter
        homeAdapter.items = feedList

        feedRecyclerView.setHasFixedSize(true)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        feedRecyclerView.adapter = homeAdapter

        observeLiveData()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            isLoadedStory = false
            isLoadedFeed = false
            shimmer.visibility = View.VISIBLE
            viewModel.reloadLiveData()
        }
    }


    private fun observeLiveData() {
        viewModel.users.observe(viewLifecycleOwner) { resource ->
            isLoadedStory = !resource.loading
            resource.data?.let {
                Log.d("test", "Story: Success")
                storyList.clear()
                storyList.addAll(it.data)
                updateList()
            }
            resource.message?.let { onError(it, "Story") }
        }
        viewModel.posts.observe(viewLifecycleOwner) { resource ->
            isLoadedFeed = !resource.loading
            resource.data?.let {
                Log.d("test", "Post: Success")
                feedList.clear()
                feedList.addAll(it.data)
                updateList()
            }
            resource.message?.let { onError(it, "Post") }
        }
    }

    private fun onError(message: String, dataType: String) {
        shimmer.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
        Log.d("test", "Failed to load data $dataType data: $message")
        Toast.makeText(context, "Failed to load $dataType data", Toast.LENGTH_SHORT).show()
    }

    private fun updateList() {
        if (isLoadedStory && isLoadedFeed) {
            Log.d("test", "updateList: story: ${storyList.size} post: ${feedList.size}")
            val updatedItems = ArrayList<Any>()
            updatedItems.add(HomeStory(storyList))
            updatedItems.addAll(feedList)
            homeAdapter.items = updatedItems
            homeAdapter.notifyDataSetChanged()
            shimmer.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.action_bar_menu_home, menu)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                homeAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // filter recycler view when text is changed
                homeAdapter.filter.filter(newText)
                return false
            }
        })

        searchView.setOnClickListener {}
        searchView.setOnCloseListener { false }
    }
}