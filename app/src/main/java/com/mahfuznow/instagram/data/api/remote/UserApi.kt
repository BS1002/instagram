package com.mahfuznow.instagram.data.api.remote

import com.mahfuznow.instagram.data.model.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api")
    suspend fun getUserResponse(@Query("results") numberOfUser: Int): Response<User>
}
