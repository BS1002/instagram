package com.mahfuznow.instagram.repository.remote

import com.mahfuznow.instagram.model.user.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api")
    fun getUserResponse(@Query("results") numberOfUser: Int): Single<User>
}
