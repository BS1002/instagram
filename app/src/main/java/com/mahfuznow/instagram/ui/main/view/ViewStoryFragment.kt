package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mahfuznow.instagram.databinding.FragmentViewStoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewStoryFragment : Fragment() {
    private lateinit var binding: FragmentViewStoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentViewStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val users = ViewStoryFragmentArgs.fromBundle(it).users
            val position = ViewStoryFragmentArgs.fromBundle(it).position - 1

            lifecycleScope.launch {
                for (i in (position until users.size)) {
                    binding.user = users[i]
                    for (i in 1..100) {
                        binding.progress = i
                        delay(20)
                    }
                }
                withContext(Dispatchers.Main) {
                    findNavController().navigateUp()
                }
            }
        }

        binding.cancel.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}