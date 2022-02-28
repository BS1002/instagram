package com.mahfuznow.instagram.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class TagData(
    val data: List<String>
) : Parcelable