package com.mahfuznow.instagram.repository

import com.mahfuznow.instagram.model.Photo
import com.mahfuznow.instagram.model.user.User
import com.mahfuznow.instagram.repository.remote.PhotoApi
import com.mahfuznow.instagram.repository.remote.UserApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val photoApi: PhotoApi,
    private val userApi: UserApi
) {

    fun getPhotos(): Single<List<Photo>> {
        return photoApi.getPhotos()
    }

    fun getUsers(): Single<User> {
        return userApi.getUserResponse(100)
    }

}