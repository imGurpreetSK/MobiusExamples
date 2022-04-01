package com.example.mobiusdemo.counter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CounterModel(
    val count: Int
): Parcelable
