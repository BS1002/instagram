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

class MainRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDetailsApi: UserDetailsApi,
    private val postApi: PostApi,
    private val tagApi: TagApi
) : Repository {

    override fun getUsersDataFlow(limit: Int, page: Int) = flow<Resource<UsersData>> {
        emit(Resource.loading())
        val usersData = userApi.getUsersData(limit, page)
        emit(Resource.success(usersData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getUsersDetails(userId: String) = flow<Resource<UserDetails>> {
        emit(Resource.loading())
        val userDetails = userDetailsApi.getUsersDetails(userId)
        emit(Resource.success(userDetails))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getPostDataFlow(limit: Int, page: Int) = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = postApi.getPostsData(limit, page)
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getPostDataByTagFlow(tag: String) = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = postApi.getPostsDataByTag(tag)
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getPostDataByUserFlow(userId: String) = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = postApi.getPostsDataByUser(userId)
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getTagDataFlow() = flow<Resource<TagData>> {
        emit(Resource.loading())
        val tagData = tagApi.getTagData()
        emit(Resource.success(tagData))
    }.catch {
        emit(Resource.error(it.message))
    }
}