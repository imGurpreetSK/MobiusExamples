package com.example.mobiusdemo.stranger

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StrangerModel(
    val name: String,
    val memesCount: Int
) : Parcelable
