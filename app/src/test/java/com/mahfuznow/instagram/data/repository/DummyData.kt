package com.mahfuznow.instagram.data.repository

import com.mahfuznow.instagram.data.model.PostsData
import com.mahfuznow.instagram.data.model.TagData
import com.mahfuznow.instagram.data.model.UserDetails
import com.mahfuznow.instagram.data.model.UsersData

object DummyData {
    val usersData = UsersData(
        arrayListOf(
            UsersData.Data(
                "Kitty",
                "60d0fe4f5311236168a10a25",
                "Steward",
                "https://randomuser.me/api/portraits/med/women/78.jpg",
                "ms"
            )
        ),
        10,
        9,
        99
    )

    val userDetails = UserDetails(
        "1955-07-19T00:57:14.606Z",
        "kent.brewer@example.com",
        "Kent",
        "male",
        "60d0fe4f5311236168a109d1",
        "Brewer",
        UserDetails.Location(
            "Buncrana",
            "Ireland",
            "Roscommon",
            "4015, Station Road",
            "+6:00"
        ),
        "021-351-5186",
        "https://randomuser.me/api/portraits/med/men/52.jpg",
        "2021-06-21T21:02:08.506Z",
        "mr",
        "2021-06-21T21:02:08.506Z"
    )

    val postData = PostsData(
        arrayListOf(
            PostsData.Data(
                "60d21bbb67d0d8992e610dda",
                "https://img.dummyapi.io/photo-1491545437994-ebc9551b87d7.jpg",
                308,
                PostsData.Data.Owner(
                    "Miguel",
                    "60d0fe4f5311236168a109dd",
                    "Lima",
                    "https://randomuser.me/api/portraits/med/men/31.jpg",
                    "mr"
                ),
                "2019-11-10T08:51:26.370Z",
                arrayListOf("water", "winter", "dog"),
                "short-coated beige dog"
            ),
        ),
        20,
        43,
        873
    )

    val tagData = TagData(
        arrayListOf(
            "dog",
            "water",
            "winter",
            "black-and-white"
        )
    )
}