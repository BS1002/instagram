package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.data.repository.Repository
import com.mahfuznow.instagram.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Observables
    private var _users: MutableLiveData<LoadingState<UsersData>> = MutableLiveData()
    val users: LiveData<LoadingState<UsersData>> get() = _users

    private var _posts: MutableLiveData<LoadingState<PostsData>> = MutableLiveData()
    val posts: LiveData<LoadingState<PostsData>> get() = _posts

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
            repository.getUsersDataFlow().collect {
                _users.value = it
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.getPostDataFlow().collect {
                _posts.value = it
            }
        }
    }
}