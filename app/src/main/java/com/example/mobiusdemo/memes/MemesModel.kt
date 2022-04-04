package com.example.mobiusdemo.memes

import android.os.Parcelable
import com.example.mobiusdemo.stranger.Meme
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemesModel(
    val memes: List<Meme>
) : Parcelable
