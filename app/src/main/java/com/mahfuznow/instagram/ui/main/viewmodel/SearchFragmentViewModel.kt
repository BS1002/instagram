package com.mahfuznow.instagram.ui.main.viewmodel

import androidx.lifecycle.*
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.repository.Repository
import com.mahfuznow.instagram.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Observables
    val tags = repository.getTagDataFlow().asLiveData()

    private val _tagTrigger = MutableLiveData<String>()

    val posts: LiveData<Resource<PostsData>> = Transformations.switchMap(_tagTrigger) {
        repository.getPostDataByTagFlow(it).asLiveData()
    }

    init {
        search("water")
    }

    fun search(searchText: String) {
        _tagTrigger.value = searchText
    }

    private var searchJob: Job? = null
    fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            search(searchText)
        }
    }

    fun reloadData() {
        _tagTrigger.value = _tagTrigger.value
    }
}