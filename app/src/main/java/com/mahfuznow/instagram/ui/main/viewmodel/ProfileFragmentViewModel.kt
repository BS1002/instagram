package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.lifecycle.*
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.UserDetails
import com.mahfuznow.instagram.data.repository.Repository
import com.mahfuznow.instagram.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val userId = MutableLiveData<String>()

    val userDetails: LiveData<Resource<UserDetails>> = Transformations.switchMap(userId) {
        repository.getUsersDetails(it).asLiveData()
    }
    val userPostsData: LiveData<Resource<PostsData>> = Transformations.switchMap(userId) {
        repository.getPostDataByUserFlow(it).asLiveData()
    }

    init {
        userId.value = "60d0fe4f5311236168a109d1"
    }

    fun overrideUserId(userId: String) {
        this.userId.value = userId
    }

    fun reloadData() {
        userId.value = userId.value
    }
}