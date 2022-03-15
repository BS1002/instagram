package com.mahfuznow.instagram.data.repository

import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.TagData
import com.mahfuznow.instagram.data.model.UserDetails
import com.mahfuznow.instagram.data.model.UsersData
import com.mahfuznow.instagram.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DummyRepository : Repository {

    override fun getUsersDataFlow(limit: Int, page: Int) = flow<Resource<UsersData>> {
        emit(Resource.loading())
        val usersData = DummyData.usersData
        emit(Resource.success(usersData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getUsersDetails(userId: String) = flow<Resource<UserDetails>> {
        emit(Resource.loading())
        val userDetails = DummyData.userDetails
        emit(Resource.success(userDetails))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getPostDataFlow(limit: Int, page: Int) = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = DummyData.postData
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getPostDataByTagFlow(tag: String) = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = DummyData.postData
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getPostDataByUserFlow(userId: String) = flow<Resource<PostsData>> {
        emit(Resource.loading())
        val postsData = DummyData.postData
        emit(Resource.success(postsData))
    }.catch {
        emit(Resource.error(it.message))
    }

    override fun getTagDataFlow() = flow<Resource<TagData>> {
        emit(Resource.loading())
        val tagData = DummyData.tagData
        emit(Resource.success(tagData))
    }.catch {
        emit(Resource.error(it.message))
    }

}