package com.mahfuznow.instagram.data.repository

import com.mahfuznow.instagram.data.api.remote.PostApi
import com.mahfuznow.instagram.data.api.remote.UserApi
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.UsersData
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val postApi: PostApi,
    private val userApi: UserApi
) {

    suspend fun getPostsData(limit: Int = 20, page: Int = 1): Response<PostsData> {
        return postApi.getPostsData(limit, page)
    }

    suspend fun getUsersData(limit: Int = 10, page: Int = 1): Response<UsersData> {
        return userApi.getUsersData(limit, page)
    }

}