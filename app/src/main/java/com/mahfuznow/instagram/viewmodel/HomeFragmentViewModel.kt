package com.mahfuznow.instagram.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahfuznow.instagram.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Observables
    var photos: MutableLiveData<List<Any>> = MutableLiveData()
    var isErrorPhotoLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var userResults: MutableLiveData<List<Any>> = MutableLiveData()
    var isErrorUserLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchUsers()
        fetchPhoto()
    }

    fun reFetchData() {
        userResults.value = ArrayList()
        photos.value = ArrayList()
        fetchData()
    }


    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = repository.getUsers()
                if (response.isSuccessful) {
                    Log.d("test", "fetchUsers: Loaded Successfully")
                    userResults.value = response.body()?.results
                    isErrorUserLiveData.value = false
                } else {
                    isErrorUserLiveData.value = true
                }
            } catch (e: Exception) {
                Log.d("test", "fetchUsers: ${e.message}")
                isErrorUserLiveData.value = true
            }
        }
    }

    private fun fetchPhoto() {
        viewModelScope.launch {
            try {
                val response = repository.getPhotos()
                if (response.isSuccessful) {
                    Log.d("test", "fetchPhoto: Loaded Successfully")
                    photos.value = response.body()
                    isErrorPhotoLiveData.value = false
                } else {
                    isErrorPhotoLiveData.value = true
                }
            } catch (e: Exception) {
                Log.d("test", "fetchPhoto: ${e.message}")
                isErrorPhotoLiveData.value = true
            }
        }
    }
}