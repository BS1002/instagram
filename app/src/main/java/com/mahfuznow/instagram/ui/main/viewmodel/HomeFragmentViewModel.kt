package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.lifecycle.*
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.data.repository.Repository
import com.mahfuznow.instagram.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Observables
    private val reloadTrigger = MutableLiveData<Boolean>()

    private var userPage = (0 until 10).random()
    private var postPage = (0 until 40).random()

    val users: LiveData<Resource<UsersData>> = Transformations.switchMap(reloadTrigger) {
        repository.getUsersDataFlow(page = userPage).asLiveData()
    }
    val posts: LiveData<Resource<PostsData>> = Transformations.switchMap(reloadTrigger) {
        repository.getPostDataFlow(page = postPage).asLiveData()
    }

    init {
        reloadLiveData()
    }

    fun reloadLiveData() {
        userPage = (0 until 10).random()
        postPage = (0 until 40).random()
        reloadTrigger.value = true
    }
}