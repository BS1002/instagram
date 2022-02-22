package com.mahfuznow.instagram.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahfuznow.instagram.model.Photo
import com.mahfuznow.instagram.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Observables
    var listItems: MutableLiveData<List<Any>> = MutableLiveData()
    var isErrorPhotoLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchPhoto()
    }

    private fun fetchPhoto() {
        repository.getPhotos()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Photo>>() {
                override fun onSuccess(items: List<Photo>) {
                    Log.d("TAG", "fetchPhoto: Loaded Successfully")
                    listItems.value = items
                    isErrorPhotoLiveData.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d("TAG", "fetchPhoto: Failed")
                    isErrorPhotoLiveData.value = true
                }
            })
    }
}