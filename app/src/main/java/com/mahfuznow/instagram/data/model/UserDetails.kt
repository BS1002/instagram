package com.mahfuznow.instagram.data.model


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserDetails(
    val dateOfBirth: String, // 1955-07-19T00:57:14.606Z
    val email: String, // kent.brewer@example.com
    val firstName: String, // Kent
    val gender: String, // male
    val id: String, // 60d0fe4f5311236168a109d1
    val lastName: String, // Brewer
    val location: Location,
    val phone: String, // 021-351-5186
    val picture: String, // https://randomuser.me/api/portraits/med/men/52.jpg
    val registerDate: String, // 2021-06-21T21:02:08.506Z
    val title: String, // mr
    val updatedDate: String // 2021-06-21T21:02:08.506Z
) : Parcelable {
    @Parcelize
    data class Location(
        val city: String, // Buncrana
        val country: String, // Ireland
        val state: String, // Roscommon
        val street: String, // 4015, Station Road
        val timezone: String // +6:00
    ) : Parcelable
}