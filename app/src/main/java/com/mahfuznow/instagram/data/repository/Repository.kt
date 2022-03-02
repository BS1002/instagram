package com.mahfuznow.instagram.data.repository

import com.mahfuznow.instagram.data.api.remote.PostApi
import com.mahfuznow.instagram.data.api.remote.TagApi
import com.mahfuznow.instagram.data.api.remote.UserApi
import com.mahfuznow.instagram.data.api.remote.UserDetailsApi
import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.TagData
import com.mahfuznow.instagram.data.model.UserDetails
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.util.LoadingState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val userApi: UserApi,
    private val userDetailsApi: UserDetailsApi,
    private val postApi: PostApi,
    private val tagApi: TagApi
) {

    fun getUsersDataFlow(limit: Int = 10, page: Int = 1) = flow<LoadingState<UsersData>> {
        emit(LoadingState.loading())
        val usersData = userApi.getUsersData(limit, page)
        emit(LoadingState.success(usersData))
    }.catch {
        emit(LoadingState.error(it))
    }

    fun getUsersDetails(userId: String = "60d0fe4f5311236168a109d1") = flow<LoadingState<UserDetails>> {
        emit(LoadingState.loading())
        val userDetails = userDetailsApi.getUsersDetails(userId)
        emit(LoadingState.success(userDetails))
    }.catch {
        emit(LoadingState.error(it))
    }

    fun getPostDataFlow(limit: Int = 20, page: Int = 1) = flow<LoadingState<PostsData>> {
        emit(LoadingState.loading())
        val postsData = postApi.getPostsData(limit, page)
        emit(LoadingState.success(postsData))
    }.catch {
        emit(LoadingState.error(it))
    }

    fun getPostDataByTagFlow(tag: String = "water") = flow<LoadingState<PostsData>> {
        emit(LoadingState.loading())
        val postsData = postApi.getPostsDataByTag(tag)
        emit(LoadingState.success(postsData))
    }.catch {
        emit(LoadingState.error(it))
    }

    fun getPostDataByUserFlow(userId: String = "60d0fe4f5311236168a109d1") = flow<LoadingState<PostsData>> {
        emit(LoadingState.loading())
        val postsData = postApi.getPostsDataByUser(userId)
        emit(LoadingState.success(postsData))
    }.catch {
        emit(LoadingState.error(it))
    }

    fun getTagDataFlow() = flow<LoadingState<TagData>> {
        emit(LoadingState.loading())
        val tagData = tagApi.getTagData()
        emit(LoadingState.success(tagData))
    }.catch {
        emit(LoadingState.error(it))
    }
}