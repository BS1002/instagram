package com.mahfuznow.instagram.data.repository

import com.mahfuznow.instagram.data.model.Photo
import com.mahfuznow.instagram.data.model.user.User
import com.mahfuznow.instagram.data.api.remote.PhotoApi
import com.mahfuznow.instagram.data.api.remote.UserApi
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val photoApi: PhotoApi,
    private val userApi: UserApi
) {

    suspend fun getPhotos(): Response<List<Photo>> {
        return photoApi.getPhotos()
    }

    suspend fun getUsers(): Response<User> {
        return userApi.getUserResponse(100)
    }

}