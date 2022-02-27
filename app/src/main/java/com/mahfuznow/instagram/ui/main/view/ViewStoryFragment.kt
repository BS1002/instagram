package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mahfuznow.instagram.databinding.FragmentViewStoryBinding

class ViewStoryFragment : Fragment() {
    private lateinit var binding: FragmentViewStoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentViewStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val user = ViewStoryFragmentArgs.fromBundle(it).user
            binding.user = user
        }

        binding.cancel.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}