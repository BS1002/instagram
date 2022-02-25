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
    private val reloadTrigger = MutableLiveData<Boolean>()

    val users: LiveData<LoadingState<UsersData>> = Transformations.switchMap(reloadTrigger) {
        repository.getUsersDataFlow().asLiveData()
    }
    val posts: LiveData<LoadingState<PostsData>> = Transformations.switchMap(reloadTrigger) {
        repository.getPostDataFlow().asLiveData()
    }

    init {
        reloadLiveData()
    }

    fun reloadLiveData() {
        reloadTrigger.value = true
    }
}