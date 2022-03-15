package com.mahfuznow.instagram.data.repository

import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.TagData
import com.mahfuznow.instagram.data.model.UserDetails
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getUsersDataFlow(limit: Int = 10, page: Int = 1): Flow<Resource<UsersData>>
    fun getUsersDetails(userId: String = "60d0fe4f5311236168a109d1"): Flow<Resource<UserDetails>>
    fun getPostDataFlow(limit: Int = 20, page: Int = 1): Flow<Resource<PostsData>>
    fun getPostDataByTagFlow(tag: String = "water"): Flow<Resource<PostsData>>
    fun getPostDataByUserFlow(userId: String = "60d0fe4f5311236168a109d1"): Flow<Resource<PostsData>>
    fun getTagDataFlow(): Flow<Resource<TagData>>
}