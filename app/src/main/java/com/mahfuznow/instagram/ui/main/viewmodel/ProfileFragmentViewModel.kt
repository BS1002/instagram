package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.lifecycle.*
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.UserDetails
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.data.repository.Repository
import com.mahfuznow.instagram.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val userId = MutableLiveData<String>()

    val userDetails: LiveData<LoadingState<UserDetails>> = Transformations.switchMap(userId) {
        repository.getUsersDetails(it).asLiveData()
    }
    val userPostsData: LiveData<LoadingState<PostsData>> = Transformations.switchMap(userId) {
        repository.getPostDataByUserFlow(it).asLiveData()
    }

    init {
        fetchProfile()
    }

    fun fetchProfile(userId: String = "60d0fe4f5311236168a109d1") {
        this.userId.value = userId
    }
}