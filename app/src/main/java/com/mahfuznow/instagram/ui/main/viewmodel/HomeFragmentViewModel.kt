package com.mahfuznow.instagram.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Observables
    var _posts: MutableLiveData<List<PostsData.Data>> = MutableLiveData()
    var _isErrorPosts: MutableLiveData<Boolean> = MutableLiveData()

    var _users: MutableLiveData<List<UsersData.Data>> = MutableLiveData()
    var _isErrorUsers: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchUsers()
        fetchPosts()
    }

    fun reFetchData() {
        fetchData()
    }


    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = repository.getUsersData()
                if (response.isSuccessful) {
                    Log.d("test", "fetchUsers: Loaded Successfully")
                    _users.value = response.body()?.data
                    _isErrorUsers.value = false
                } else {
                    _isErrorUsers.value = true
                }
            } catch (e: Exception) {
                Log.d("test", "fetchUsers: ${e.message}")
                _isErrorUsers.value = true
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val response = repository.getPostsData()
                if (response.isSuccessful) {
                    Log.d("test", "fetchPosts: Loaded Successfully")
                    _posts.value = response.body()?.data
                    _isErrorPosts.value = false
                } else {
                    _isErrorPosts.value = true
                }
            } catch (e: Exception) {
                Log.d("test", "fetchPosts: ${e.message}")
                _isErrorPosts.value = true
            }
        }
    }
}