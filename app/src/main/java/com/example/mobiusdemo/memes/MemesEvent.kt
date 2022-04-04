package com.example.mobiusdemo.memes

import com.example.mobiusdemo.stranger.Meme

sealed class MemesEvent {
    class MemesFetched(val memes: List<Meme>) : MemesEvent()
    object Retry : MemesEvent()
    object FetchFailed : MemesEvent()
}
