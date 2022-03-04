package com.mahfuznow.instagram.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.databinding.BottomSheetDialogLayoutBinding
import com.mahfuznow.instagram.databinding.FragmentProfileBinding
import com.mahfuznow.instagram.ui.main.adapter.ProfileAdapter
import com.mahfuznow.instagram.ui.main.viewmodel.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileFragmentViewModel by viewModels()


    @Inject
    lateinit var profileAdapter: ProfileAdapter
    private var postList: ArrayList<Any> = ArrayList()

    private lateinit var binding: FragmentProfileBinding
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorLayout: LinearLayout

    private var isLoadedUserDetails = false
    private var isLoadedPost = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        arguments?.let {
            val userId = ProfileFragmentArgs.fromBundle(it).userId
            userId?.let { id ->
                //Loading another user profile
                viewModel.overrideUserId(id)
                setHasOptionsMenu(false)
            }
        }

        shimmer = binding.shimmer
        nestedScrollView = binding.nestedScrollView
        recyclerView = binding.recyclerView
        errorLayout = binding.errorLayout.root

        //items is a field defined in super class of the adapter
        profileAdapter.items = postList

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = profileAdapter

        swipeRefreshLayout = binding.swipeRefreshLayout
        val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            shimmer.visibility = View.VISIBLE
            viewModel.reloadData()
        }
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
        binding.errorLayout.tryAgain.setOnClickListener { onRefreshListener.onRefresh() }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.userDetails.observe(viewLifecycleOwner) { resource ->
            isLoadedUserDetails = !resource.loading
            resource.data?.let {
                Log.d("test", "User: Success")
                binding.userDetails = it
                updateList()
            }
            resource.message?.let { onError(it, "User") }
        }

        viewModel.userPostsData.observe(viewLifecycleOwner) { resource ->
            isLoadedPost = !resource.loading
            resource.data?.let {
                Log.d("test", "Post: Success")
                postList.clear()
                postList.addAll(it.data)
                updateList()
            }
            resource.message?.let { onError(it, "Post") }
        }
    }


    private fun onError(message: String, dataType: String) {
        shimmer.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
        nestedScrollView.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        Log.d("test", "Failed to load data $dataType data: $message")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateList() {
        if (isLoadedUserDetails && isLoadedPost) {
            Log.d("test", "updateList: ${postList.size} items")
            shimmer.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            errorLayout.visibility = View.GONE
            nestedScrollView.visibility = View.VISIBLE

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
                super.onOptionsItemSelected(item)
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