package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.lifecycle.*
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.data.repository.Repository
import com.mahfuznow.instagram.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Observables
    val tags = repository.getTagDataFlow().asLiveData()

    val tagTrigger = MutableLiveData<String>()

    val posts: LiveData<LoadingState<PostsData>> = Transformations.switchMap(tagTrigger) {
        repository.getPostDataByTagFlow(it).asLiveData()
    }

    init {
        tagTrigger.value = "water"
    }
}