package com.mahfuznow.instagram.data.api.remote

import com.mahfuznow.instagram.data.model.UsersData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("user")
    suspend fun getUsersData(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): UsersData

}
