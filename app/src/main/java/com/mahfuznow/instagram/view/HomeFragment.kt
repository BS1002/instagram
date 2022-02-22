package com.mahfuznow.instagram.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahfuznow.instagram.adapter.HomeListDelegationAdapter
import com.mahfuznow.instagram.databinding.FragmentHomeBinding
import com.mahfuznow.instagram.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()
    private var listItems: ArrayList<Any> = ArrayList()

    @Inject
    lateinit var adapter: HomeListDelegationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.feedRecyclerView

        //items is a field defined in super class of the adapter
        adapter.items = listItems

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        observeLiveData()
    }


    private fun observeLiveData() {
        viewModel.listItems.observe(
            viewLifecycleOwner
        ) { listItems ->
            this.listItems.addAll(listItems)
            //Toast.makeText(this, "Data loaded successfully", Toast.LENGTH_SHORT).show()
            setValues()
        }
        viewModel.isErrorPhotoLiveData.observe(
            viewLifecycleOwner
        ) { isError ->
            if (isError)
                onError("Photo")
        }
    }

    private fun onError(msg: String) {
        Toast.makeText(context, "Failed to load data $msg's data", Toast.LENGTH_SHORT).show()
    }

    private fun setValues() {
        adapter.items = listItems //IMPORTANT
        adapter.notifyDataSetChanged()
    }

}