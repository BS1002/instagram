package com.mahfuznow.instagram.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.databinding.FragmentSearchBinding
import com.mahfuznow.instagram.ui.main.adapter.SearchAdapter
import com.mahfuznow.instagram.ui.main.viewmodel.SearchFragmentViewModel
import com.mahfuznow.instagram.util.LoadingState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchFragmentViewModel by viewModels()
    private val tags: ArrayList<String> = ArrayList()
    private var lastTag: String = "water"

    //Adapter for SearchView Autocomplete
    private var tagAdapter: ArrayAdapter<String>? = null

    private var feedList: ArrayList<Any> = ArrayList()

    @Inject
    lateinit var searchAdapter: SearchAdapter

    private lateinit var binding: FragmentSearchBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    var isLoadedTag = false
    var isLoadedPost = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        //items is a field defined in super class of the adapter
        searchAdapter.items = feedList

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = searchAdapter

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            requestPostByTag(lastTag)
        }

        //Enabling Action Menu
        setHasOptionsMenu(true)

        observeLiveData()
    }


    private fun requestPostByTag(tag: String) {
        swipeRefreshLayout.isRefreshing = true
        viewModel.tagTrigger.value = tag
        lastTag = tag
    }

    private fun observeLiveData() {
        viewModel.tags.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingState.Error -> onError(it.e, "Tags")
                is LoadingState.Loading -> Log.d("test", "Tags: Loading")
                is LoadingState.Success -> {
                    Log.d("test", "Tags: Success")
                    isLoadedTag = true
                    tags.clear()
                    tags.addAll(it.data.data)
                    tagAdapter?.addAll(tags)
                    tagAdapter?.notifyDataSetChanged()
                }
            }

        }

        viewModel.posts.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingState.Error -> onError(it.e, "Posts")
                is LoadingState.Loading -> Log.d("test", "Posts: Loading")
                is LoadingState.Success -> {
                    Log.d("test", "Posts: Success")
                    isLoadedPost = true
                    feedList.clear()
                    feedList.addAll(it.data.data)
                    updateList()
                }
            }

        }
    }


    private fun onError(e: Throwable, msg: String) {
        swipeRefreshLayout.isRefreshing = false
        Log.d("test", "Failed to load data ${e.message} data")
        Toast.makeText(context, "Failed to load $msg data", Toast.LENGTH_SHORT).show()
    }

    private fun updateList() {
        if (isLoadedTag && isLoadedPost) {
            Log.d("test", "updateList: $feedList")
            swipeRefreshLayout.isRefreshing = false

            searchAdapter.items = feedList
            searchAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.action_bar_menu_search, menu)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                requestPostByTag(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })

        val searchAutoComplete: SearchView.SearchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        //searchAutoComplete.dropDownAnchor = R.id.action_search
        searchAutoComplete.threshold = 1 //After how many character show Suggestion
        tagAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tags)
        searchAutoComplete.setAdapter(tagAdapter)

        searchAutoComplete.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val searchString = parent.getItemAtPosition(position) as String
            searchAutoComplete.setText(searchString)

            //Request Post by Tag
            requestPostByTag(searchString.trim())
        }
    }
}