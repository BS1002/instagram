package com.mahfuznow.instagram.data.api.remote

import com.mahfuznow.instagram.data.model.UserDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailsApi {
    @GET("user/{user-id}")
    suspend fun getUsersDetails(@Path(value = "user-id", encoded = true) userId: String): UserDetails
}
