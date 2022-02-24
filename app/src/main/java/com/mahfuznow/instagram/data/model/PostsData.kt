package com.mahfuznow.instagram.data.model


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PostsData(
    val data: List<Data>,
    val limit: Int, // 20
    val page: Int, // 43
    val total: Int // 873
) : Parcelable {
    @Parcelize
    data class Data(
        val id: String, // 60d21bbb67d0d8992e610dda
        val image: String, // https://img.dummyapi.io/photo-1491545437994-ebc9551b87d7.jpg
        val likes: Int, // 308
        val owner: Owner,
        val publishDate: String, // 2019-11-10T08:51:26.370Z
        val tags: List<String>,
        val text: String // short-coated beige dog
    ) : Parcelable {
        @Parcelize
        data class Owner(
            val firstName: String, // Miguel
            val id: String, // 60d0fe4f5311236168a109dd
            val lastName: String, // Lima
            val picture: String, // https://randomuser.me/api/portraits/med/men/31.jpg
            val title: String // mr
        ) : Parcelable
    }
}