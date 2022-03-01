package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mahfuznow.instagram.databinding.FragmentPostBinding


class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Select Action")
            val items = arrayOf(
                "Select photo from Gallery",
                "Capture photo from Camera"
            )
            alertDialog.setItems(items) { dialog, which ->
                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }
            alertDialog.show()
        }

        binding.camera.setOnClickListener {
            camera()
        }

        binding.gallery.setOnClickListener {
            gallery()
        }
    }

    private val getContentLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imageView.setImageURI(it)
    }

    private fun gallery() {
        getContentLauncher.launch("image/*")
    }

    private val takePicturePreviewLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        binding.imageView.setImageBitmap(it)
    }

    private fun camera() {
        takePicturePreviewLauncher.launch()
    }
}