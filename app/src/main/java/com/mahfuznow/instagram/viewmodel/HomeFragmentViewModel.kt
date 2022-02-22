package com.mahfuznow.instagram.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahfuznow.instagram.model.Photo
import com.mahfuznow.instagram.model.user.User
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
    var photos: MutableLiveData<List<Any>> = MutableLiveData()
    var isErrorPhotoLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var userResults: MutableLiveData<List<Any>> = MutableLiveData()
    var isErrorUserLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchData()
    }

    private fun fetchData() {
        fetchUsers()
        fetchPhoto()
    }

    fun reFetchData() {
        userResults.value = ArrayList()
        photos.value = ArrayList()
        fetchData()
    }


    private fun fetchUsers() {
        repository.getUsers()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<User>() {
                override fun onSuccess(user: User) {
                    Log.d("TAG", "fetchUsers: Loaded Successfully")
                    userResults.value = user.results
                    isErrorUserLiveData.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d("TAG", "fetchUsers: Failed")
                    isErrorUserLiveData.value = true
                }
            })
    }

    private fun fetchPhoto() {
        repository.getPhotos()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Photo>>() {
                override fun onSuccess(items: List<Photo>) {
                    Log.d("TAG", "fetchPhoto: Loaded Successfully")
                    photos.value = items
                    isErrorPhotoLiveData.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d("TAG", "fetchPhoto: Failed")
                    isErrorPhotoLiveData.value = true
                }
            })
    }
}