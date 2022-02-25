package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.lifecycle.*
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
    val users: LiveData<LoadingState<UsersData>> = repository.getUsersDataFlow().asLiveData()
    val posts: LiveData<LoadingState<PostsData>> = repository.getPostDataFlow().asLiveData()
}