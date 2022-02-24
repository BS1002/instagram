package com.mahfuznow.instagram.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersData(
    val data: List<Data>,
    val limit: Int, // 10
    val page: Int, // 9
    val total: Int // 99
) : Parcelable {
    @Parcelize
    data class Data(
        val firstName: String, // Kitty
        val id: String, // 60d0fe4f5311236168a10a25
        val lastName: String, // Steward
        val picture: String, // https://randomuser.me/api/portraits/med/women/78.jpg
        val title: String // ms
    ) : Parcelable
}