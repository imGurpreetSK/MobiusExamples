package com.example.mobiusdemo.memes

sealed class MemesEffect {
    object LoadMemes : MemesEffect()

    sealed class MemesViewEffect : MemesEffect()
    object Loading : MemesViewEffect()
    object MemesFetchSuccessful : MemesViewEffect()
    object ShowRetry : MemesViewEffect()
}
