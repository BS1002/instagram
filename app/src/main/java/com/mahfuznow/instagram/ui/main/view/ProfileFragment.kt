package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.databinding.BottomSheetDialogLayoutBinding
import com.mahfuznow.instagram.databinding.FragmentProfileBinding
import com.mahfuznow.instagram.ui.main.adapter.ProfileAdapter
import com.mahfuznow.instagram.ui.main.viewmodel.ProfileFragmentViewModel
import com.mahfuznow.instagram.util.LoadingState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileFragmentViewModel by viewModels()

    private var userId: String? = null

    @Inject
    lateinit var profileAdapter: ProfileAdapter
    private var postList: ArrayList<Any> = ArrayList()

    private lateinit var binding: FragmentProfileBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private var isLoadedUserDetails = false
    private var isLoadedPost = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val userId = ProfileFragmentArgs.fromBundle(it).userId
            this.userId = userId
        }

        requestUserProfile()

        recyclerView = binding.recyclerView
        //items is a field defined in super class of the adapter
        profileAdapter.items = postList

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = profileAdapter

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            requestUserProfile()
        }

        observeLiveData()
    }

    private fun requestUserProfile() {
        userId?.let {
            viewModel.fetchProfile(it)
        } ?: run {
            viewModel.fetchProfile()

            //Enabling Action Menu
            setHasOptionsMenu(true)
        }
    }

    private fun observeLiveData() {
        viewModel.userDetails.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingState.Error -> onError(it.e, "UserDetails")
                is LoadingState.Loading -> Log.d("test", "UserDetails: Loading")
                is LoadingState.Success -> {
                    Log.d("test", "UserDetails: Success")
                    isLoadedUserDetails = true
                    binding.userDetails = it.data
                }
            }

        }

        viewModel.userPostsData.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingState.Error -> onError(it.e, "Posts")
                is LoadingState.Loading -> Log.d("test", "Posts: Loading")
                is LoadingState.Success -> {
                    Log.d("test", "Posts: Success")
                    isLoadedPost = true
                    postList.clear()
                    postList.addAll(it.data.data)
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
        if (isLoadedUserDetails && isLoadedPost) {
            Log.d("test", "updateList: $postList")
            swipeRefreshLayout.isRefreshing = false
            profileAdapter.items = postList
            profileAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.action_bar_menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_hamburger -> {
                showBottomSheetDialog()
                true
            }
            else -> {
                true
            }
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetDialogLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()

        bottomSheetBinding.settings.setOnClickListener {
            bottomSheetDialog.dismiss()
            findNavController().navigate(ProfileFragmentDirections.actionProfileToSettingsFragment())
        }

    }
}