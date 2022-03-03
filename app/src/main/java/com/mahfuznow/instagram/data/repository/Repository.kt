package com.mahfuznow.instagram.data.repository

import com.mahfuznow.instagram.data.api.remote.PostApi
import com.mahfuznow.instagram.data.api.remote.TagApi
import com.mahfuznow.instagram.data.api.remote.UserApi
import com.mahfuznow.instagram.data.api.remote.UserDetailsApi
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.TagData
import com.mahfuznow.instagram.data.model.UserDetails
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val userApi: UserApi,
    private val userDetailsApi: UserDetailsApi,
    private val postApi: PostApi,
    private val tagApi: TagApi
) {

    fun getUsersDataFlow(limit: Int = 10, page: Int = 1) = flow<Resource<UsersData>> {
        emit(Resource.loading())
        val usersData = userApi.getUsersData(limit, page)
        emit(Resource.success(usersData))
    }.catch {
        emit(Resource.error(it.message))
    }

    fun getUsersDetails(userId: String = "60d0fe4f5311236168a109d1") = flow<Resource<UserDetails>> {
        emit(Resource.loading())
        val userDetails = userDetailsApi.getUsersDetails(userId)
        emit(Resource.success(userDetails))
    }.catch {
        emit(Resource.error(it.message))
    }

    fun getPostDataFlow(limit: Int = 20, page: Int = 1) = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = postApi.getPostsData(limit, page)
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    fun getPostDataByTagFlow(tag: String = "water") = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = postApi.getPostsDataByTag(tag)
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    fun getPostDataByUserFlow(userId: String = "60d0fe4f5311236168a109d1") = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = postApi.getPostsDataByUser(userId)
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    fun getTagDataFlow() = flow<Resource<TagData>> {
        emit(Resource.loading())
        val tagData = tagApi.getTagData()
        emit(Resource.success(tagData))
    }.catch {
        emit(Resource.error(it.message))
    }
}