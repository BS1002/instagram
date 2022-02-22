package com.mahfuznow.instagram.repository

import com.mahfuznow.instagram.model.Photo
import com.mahfuznow.instagram.repository.remote.PhotoApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val photoApi: PhotoApi
) {

    fun getPhotos(): Single<List<Photo>> {
        return photoApi.getPhotos()
    }

}