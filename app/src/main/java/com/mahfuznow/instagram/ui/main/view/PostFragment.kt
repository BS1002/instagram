package com.mahfuznow.instagram.ui.main.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mahfuznow.instagram.databinding.FragmentPostBinding


class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding

    companion object {
        private const val CAMERA_REQUEST_CODE = 1
        private const val GALLERY_REQUEST_CODE = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //when you click on the image
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

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    binding.imageView.setImageBitmap(bitmap)
                }

                GALLERY_REQUEST_CODE -> {
                    val uri = data?.data
                    binding.imageView.setImageURI(uri)

                }
            }
        }
    }

}